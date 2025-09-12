package org.example.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.database.PeopleDao
import org.example.project.database.Person

class DemoViewModel(private val dao: PeopleDao) : ViewModel() {
    private val _state = MutableStateFlow(ScreenState())
    val state = _state.asStateFlow()

    init {
        getPeople()
    }

    fun getPeople() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.getAll().collectLatest { people ->
                _state.update { it.copy(people = people) }
            }
        }
    }

    fun changeName(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(name = value) }
        }
    }

    fun addPerson() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.add(Person(name = _state.value.name))
            _state.update { it.copy(name = "") }
        }
    }

    fun deletePerson(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(person)
        }
    }
}

data class ScreenState(
    val people: List<Person> = emptyList(),
    val name: String = ""
)
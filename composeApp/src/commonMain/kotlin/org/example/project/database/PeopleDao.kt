package org.example.project.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PeopleDao {
    @Query("SELECT * FROM person ")
    fun getAll(): Flow<List<Person>>

    @Delete
    suspend fun delete(person: Person)

    @Upsert
    suspend fun add(person: Person)
}
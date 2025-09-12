package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import roomkoinproject.composeapp.generated.resources.Res
import roomkoinproject.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App(viewModel: DemoViewModel) {
    MaterialTheme {
        val state by viewModel.state.collectAsState()
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.name,
                onValueChange = viewModel::changeName
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = viewModel::addPerson,
                content = { Text("add person") }
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                items(state.people) {
                    Text(
                        modifier = Modifier.fillMaxWidth().clickable {
                            viewModel.deletePerson(it)
                        },
                        text = it.name
                    )
                }
            }
        }
    }
}
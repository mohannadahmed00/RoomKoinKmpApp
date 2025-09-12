package org.example.project

import androidx.compose.ui.window.ComposeUIViewController
import org.example.project.database.getDatabaseBuilder

fun MainViewController() = ComposeUIViewController {
    val db = getDatabaseBuilder().build()
    val viewModel = DemoViewModel(db.getPeopleDao())
    App(viewModel)
}
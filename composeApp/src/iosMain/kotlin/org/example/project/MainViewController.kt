package org.example.project

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import org.example.project.database.getDatabaseBuilder

fun MainViewController() = ComposeUIViewController {
    App(remember {
        DemoViewModel(
            getDatabaseBuilder().build().getAyaDao()
        )
    })
}
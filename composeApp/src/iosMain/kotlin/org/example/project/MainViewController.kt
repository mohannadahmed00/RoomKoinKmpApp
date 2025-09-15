package org.example.project

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import org.example.project.database.getDatabaseBuilder
import org.example.project.database.getRoomDatabase
fun MainViewController() = ComposeUIViewController {
    App(remember {
        DemoViewModel(
            getRoomDatabase(getDatabaseBuilder()).getAyaDao() 
        )
    })
}
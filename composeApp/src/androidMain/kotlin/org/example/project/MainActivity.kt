package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.database.getDatabaseBuilder
import org.example.project.database.getRoomDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val db = getDatabaseBuilder(applicationContext).build()
        val viewModel = DemoViewModel(db.getPeopleDao())
        setContent {
            App(viewModel)
        }
    }
}

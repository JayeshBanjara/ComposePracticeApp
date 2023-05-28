package com.example.demoappcompose.ui.subject

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.demoappcompose.ui.components.CustomTopAppBar

@Composable
fun SubjectScreen(navController: NavController, std: String) {
    Scaffold(
        topBar = {
            CustomTopAppBar(title = std)
        }
    ) { contentPadding ->

        print(contentPadding)

    }
}
package com.example.demoappcompose.ui.subject

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.subject.components.SubjectList

@Composable
fun SubjectScreen(navController: NavController, std: String) {

    val subjects: List<String> = listOf(
        "Mathematics",
        "Computer",
        "Hindi",
        "Social Science",
        "English",
        "Science & Technology"
    )

    Scaffold(
        topBar = {
            CustomTopAppBar(title = std,
                showBack = true,
                onBackClick = {
                    navController.popBackStack()
                })
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            if (std.toInt() > 10) {
                TabScreen(navController = navController, subjects = subjects)
            } else {
                SubjectList(subjects = subjects, navController = navController)
            }
        }
    }
}
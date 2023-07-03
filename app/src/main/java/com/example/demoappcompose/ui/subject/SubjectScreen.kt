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
fun SubjectScreen(
    navController: NavController,
    subjectsViewModel: SubjectsViewModel,
    className: String,
    classId: String,
    isStream: String
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(title = className,
                showBack = true,
                onBackClick = {
                    navController.popBackStack()
                })
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            if (isStream == "1") {
                TabScreen(
                    navController = navController,
                    subjectsViewModel = subjectsViewModel,
                    classId = classId
                )
            } else {
                SubjectList(
                    navController = navController,
                    subjectsViewModel = subjectsViewModel,
                    classId = classId
                )
            }
        }
    }
}
package com.example.demoappcompose.ui.subject

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.demoappcompose.ui.subject.components.SubjectList

@Composable
fun ArtsSubjects(
    subjects: List<String>,
    navController: NavController
) {
    SubjectList(subjects = subjects, navController = navController)
}
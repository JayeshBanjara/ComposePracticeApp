package com.example.demoappcompose.ui.subject

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.demoappcompose.ui.subject.components.SubjectList

@Composable
fun ArtsSubjects(
    navController: NavController,
    subjectsViewModel: SubjectsViewModel,
    classId: String
) {
    SubjectList(
        navController = navController,
        subjectsViewModel = subjectsViewModel,
        classId = classId
    )
}
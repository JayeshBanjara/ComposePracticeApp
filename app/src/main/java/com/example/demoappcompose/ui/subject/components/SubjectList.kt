package com.example.demoappcompose.ui.subject.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.components.Loader
import com.example.demoappcompose.ui.dashboard.my_subscription.MySubsItem
import com.example.demoappcompose.ui.subject.SubjectsViewModel
import com.example.demoappcompose.utility.UiState

@Composable
fun SubjectList(
    navController: NavController,
    subjectsViewModel: SubjectsViewModel,
    classId: String
) {

    LaunchedEffect(Unit) {
        subjectsViewModel.getSubjects(classId = classId)
    }
    
    Box(modifier = Modifier.fillMaxSize()) {

        val context = LocalContext.current

        Image(
            painter = painterResource(id = R.drawable.screen_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        val state by remember { subjectsViewModel.uiState }.collectAsStateWithLifecycle()
        when (state) {
            is UiState.Empty -> {}
            is UiState.Error -> {
                val errorMessage = (state as UiState.Error).data
                LaunchedEffect(Unit) {
                    Toast.makeText(
                        context,
                        errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            is UiState.Loading -> {
                Loader()
            }

            is UiState.Success -> {

                val subjects =
                    (state as UiState.Success).data.subjectData.subjectList

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(10.dp),
                    content = {
                        items(subjects) {
                            SubjectItem(navController = navController, subject = it)
                        }
                    }
                )

            }
        }
    }
}
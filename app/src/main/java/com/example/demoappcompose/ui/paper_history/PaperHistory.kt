package com.example.demoappcompose.ui.paper_history

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.components.Loader
import com.example.demoappcompose.ui.components.ScreenBackground
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.utility.UiState

@Composable
fun PaperHistory(
    navController: NavController,
    paperHistoryViewModel: PaperHistoryViewModel
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Paper History",
                showBack = true,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->

        val context = LocalContext.current

        Box(
            modifier = Modifier
                .padding(
                    start = screenPadding(),
                    top = innerPadding.calculateTopPadding(),
                    end = screenPadding(),
                    bottom = screenPadding()
                )
        ) {

            ScreenBackground()

            val state by remember { paperHistoryViewModel.uiState }.collectAsStateWithLifecycle()
            when(state) {
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
                    val paperHistoryList = (state as UiState.Success).data.paperHistoryData.paperHistoryList
                    LazyColumn(content = {
                        item { Spacer(modifier = Modifier.height(10.dp)) }
                        items(paperHistoryList) {
                            PaperHistoryItem(it)
                        }
                    })
                }
            }
        }
    }
}
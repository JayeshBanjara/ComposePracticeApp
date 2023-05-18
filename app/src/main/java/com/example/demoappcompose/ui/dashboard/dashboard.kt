package com.example.demoappcompose.ui.dashboard

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.dashboard.bottom_nav.DashboardBottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(navController: NavController) {
    Scaffold(
        topBar = { CustomTopAppBar(title = stringResource(id = R.string.app_name)) },
        //bottomBar = { DashboardBottomNavigation(navController = navController) }
    ) { contentPadding ->
        print(contentPadding)
    }
}
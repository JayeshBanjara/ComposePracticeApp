package com.example.demoappcompose.ui.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.dashboard.bottom_nav.DashboardBottomNavigation
import com.example.demoappcompose.ui.dashboard.bottom_nav.DashboardNavHost
import com.example.demoappcompose.ui.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(mainNavController: NavController) {
    val navController: NavHostController = rememberNavController()
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(id = R.string.app_name),
                actionIcon = painterResource(id = R.drawable.ic_user),
                onIconClick = {
                    mainNavController.navigate(Screens.EditProfile.route)
                }
            )
        },
        bottomBar = { DashboardBottomNavigation(navController = navController) }
    ) { innerPadding ->
        DashboardNavHost(
            mainNavController = mainNavController,
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
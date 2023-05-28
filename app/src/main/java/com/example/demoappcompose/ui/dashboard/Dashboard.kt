package com.example.demoappcompose.ui.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.dashboard.bottom_nav.DashboardBottomNavigation
import com.example.demoappcompose.ui.dashboard.bottom_nav.DashboardNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard() {
    val navController: NavHostController = rememberNavController()
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(id = R.string.app_name),
                actionIcon = painterResource(id = R.drawable.ic_user),
                onIconClick = {

                }
            )
        },
        bottomBar = { DashboardBottomNavigation(navController = navController) }
    ) { contentPadding ->
        DashboardNavHost(navController = navController, modifier = Modifier.padding(contentPadding))
    }
}
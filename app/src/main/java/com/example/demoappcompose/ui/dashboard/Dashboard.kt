package com.example.demoappcompose.ui.dashboard

import androidx.compose.foundation.layout.padding
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
import com.example.demoappcompose.ui.dashboard.home.HomeViewModel
import com.example.demoappcompose.ui.navigation.Screens

@Composable
fun Dashboard(
    mainNavController: NavController,
    homeViewModel: HomeViewModel,
    userId: String,
    profilePicUrl: String
) {
    val navController: NavHostController = rememberNavController()
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(id = R.string.app_name),
                imgUrl = profilePicUrl,
                onImageClick = {
                    mainNavController.navigate(Screens.EditProfile.route)
                }
            )
        },
        bottomBar = { DashboardBottomNavigation(navController = navController, userId = userId) }
    ) { innerPadding ->
        DashboardNavHost(
            mainNavController = mainNavController,
            navController = navController,
            homeViewModel = homeViewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
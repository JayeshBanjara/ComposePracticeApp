package com.example.demoappcompose.ui.dashboard.bottom_nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.demoappcompose.ui.dashboard.about_us.AboutUsScreen
import com.example.demoappcompose.ui.dashboard.contact_us.ContactUsScreen
import com.example.demoappcompose.ui.dashboard.feedback.FeedbackScreen
import com.example.demoappcompose.ui.dashboard.home.HomeScreen

@Composable
fun DashboardNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(navController = navController, startDestination = BottomNavigationScreens.Home.route) {
        composable(BottomNavigationScreens.Home.route) {
            HomeScreen(navController = navController, modifier = modifier)
        }
        composable(BottomNavigationScreens.AboutUs.route) {
            AboutUsScreen(navController = navController, modifier = modifier)
        }
        composable(BottomNavigationScreens.ContactUs.route) {
            ContactUsScreen(navController = navController, modifier = modifier)
        }
        composable(BottomNavigationScreens.Feedback.route) {
            FeedbackScreen(navController = navController, modifier = modifier)
        }
    }
}
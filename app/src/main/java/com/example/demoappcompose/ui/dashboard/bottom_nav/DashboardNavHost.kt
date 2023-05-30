package com.example.demoappcompose.ui.dashboard.bottom_nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.demoappcompose.ui.dashboard.about_us.AboutUsScreen
import com.example.demoappcompose.ui.dashboard.contact_us.ContactUsScreen
import com.example.demoappcompose.ui.dashboard.feedback.FeedbackScreen
import com.example.demoappcompose.ui.dashboard.home.HomeScreen
import com.example.demoappcompose.utility.sendMail

@Composable
fun DashboardNavHost(
    mainNavController: NavController,
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(navController = navController, startDestination = BottomNavigationScreens.Home.route) {
        composable(BottomNavigationScreens.Home.route) {
            HomeScreen(navController = mainNavController, modifier = modifier)
        }
        composable(BottomNavigationScreens.AboutUs.route) {
            AboutUsScreen(navController = mainNavController, modifier = modifier)
        }
        composable(BottomNavigationScreens.ContactUs.route) {
            ContactUsScreen(navController = mainNavController, modifier = modifier)
        }
    }
}
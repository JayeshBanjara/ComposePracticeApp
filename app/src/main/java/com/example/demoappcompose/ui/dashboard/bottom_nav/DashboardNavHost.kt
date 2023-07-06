package com.example.demoappcompose.ui.dashboard.bottom_nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.demoappcompose.ui.dashboard.about_us.AboutUsScreen
import com.example.demoappcompose.ui.dashboard.about_us.AboutUsViewModel
import com.example.demoappcompose.ui.dashboard.contact_us.ContactUsScreen
import com.example.demoappcompose.ui.dashboard.home.HomeScreen
import com.example.demoappcompose.ui.dashboard.home.HomeViewModel
import com.example.demoappcompose.ui.payment.PaymentScreen
import com.example.demoappcompose.ui.payment.PaymentViewModel

@Composable
fun DashboardNavHost(
    mainNavController: NavController,
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    modifier: Modifier
) {
    NavHost(navController = navController, startDestination = BottomNavigationScreens.Home.route) {
        composable(BottomNavigationScreens.Home.route) {
            HomeScreen(
                navController = mainNavController,
                homeViewModel = homeViewModel,
                modifier = modifier
            )
        }
        composable(BottomNavigationScreens.AboutUs.route) {

            val aboutUsViewModel = hiltViewModel<AboutUsViewModel>()

            AboutUsScreen(
                modifier = modifier,
                navController = mainNavController,
                aboutUsViewModel = aboutUsViewModel
            )
        }
        composable(BottomNavigationScreens.ContactUs.route) {
            ContactUsScreen(
                navController = mainNavController, modifier = modifier
            )
        }
        composable(route = BottomNavigationScreens.PaymentScreen.route) {

            val paymentViewModel = hiltViewModel<PaymentViewModel>()

            PaymentScreen(
                navController = navController,
                viewModel = paymentViewModel
            )
        }
    }
}
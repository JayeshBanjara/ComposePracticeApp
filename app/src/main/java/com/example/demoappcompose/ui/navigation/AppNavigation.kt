package com.example.demoappcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.demoappcompose.ui.auth.LoginScreen
import com.example.demoappcompose.ui.splash.SplashScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(route = Screens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screens.LoginScreen.route + "/{name}",
        arguments = listOf(
            navArgument("name") {
                type = NavType.StringType
                defaultValue = "Jayesh"
            }
        )
        ) { entry ->
            LoginScreen(name = entry.arguments?.getString("name"))
        }
    }
}
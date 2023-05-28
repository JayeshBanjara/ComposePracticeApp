package com.example.demoappcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.demoappcompose.ui.auth.login.LoginScreenNew
import com.example.demoappcompose.ui.auth.register.RegisterScreen
import com.example.demoappcompose.ui.dashboard.Dashboard
import com.example.demoappcompose.ui.splash.SplashScreenNew
import com.example.demoappcompose.ui.subject.SubjectScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(route = Screens.SplashScreen.route) {
            SplashScreenNew(navController = navController)
        }

        composable(route = Screens.LoginScreen.route) {
            LoginScreenNew(navController = navController)
        }

        composable(route = Screens.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }

        composable(route = Screens.Dashboard.route) {
            Dashboard(mainNavController = navController)
        }

        composable(
            route = Screens.SubjectScreen.route + "/{std}",
            arguments = listOf(
                navArgument("std") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            SubjectScreen(
                navController = navController,
                std = entry.arguments?.getString("std") ?: ""
            )
        }
    }
}
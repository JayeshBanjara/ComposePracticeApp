package com.example.demoappcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.demoappcompose.ui.auth.login.LoginScreen
import com.example.demoappcompose.ui.auth.login.LoginViewModel
import com.example.demoappcompose.ui.auth.register.RegisterScreen
import com.example.demoappcompose.ui.create_question.ChapterList
import com.example.demoappcompose.ui.create_question.CreateQuestion
import com.example.demoappcompose.ui.create_question.QuestionList
import com.example.demoappcompose.ui.dashboard.Dashboard
import com.example.demoappcompose.ui.dashboard.my_subscription.MySubscription
import com.example.demoappcompose.ui.paper_history.PaperHistory
import com.example.demoappcompose.ui.payment.PaymentScreen
import com.example.demoappcompose.ui.print_settings.PrintSettings
import com.example.demoappcompose.ui.profile.EditProfile
import com.example.demoappcompose.ui.register_purchase_book.RegisterToPurchaseBook
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
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }

        composable(route = Screens.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }

        composable(
            route = Screens.Dashboard.route + "/{userId}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            Dashboard(
                mainNavController = navController,
                userId = entry.arguments?.getString("userId") ?: ""
            )
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

        composable(route = Screens.EditProfile.route) {
            EditProfile(navController = navController)
        }

        composable(route = Screens.MySubscription.route) {
            MySubscription(navController = navController)
        }

        composable(route = Screens.RegisterPurchaseBook.route) {
            RegisterToPurchaseBook(navController = navController)
        }

        composable(route = Screens.PaperHistory.route) {
            PaperHistory(navController = navController)
        }

        composable(
            route = Screens.CreateQuestion.route + "/{subjectName}",
            arguments = listOf(
                navArgument("subjectName") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            CreateQuestion(
                navController = navController,
                subjectName = entry.arguments?.getString("subjectName") ?: ""
            )
        }

        composable(route = Screens.PrintSettings.route) {
            PrintSettings(navController = navController)
        }

        composable(
            route = Screens.ChapterList.route + "/{subjectName}",
            arguments = listOf(
                navArgument("subjectName") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            ChapterList(
                navController = navController,
                subjectName = entry.arguments?.getString("subjectName") ?: ""
            )
        }

        composable(
            route = Screens.QuestionList.route + "/{chapterName}",
            arguments = listOf(
                navArgument("chapterName") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            QuestionList(
                navController = navController,
                chapterName = entry.arguments?.getString("chapterName") ?: ""
            )
        }
    }
}
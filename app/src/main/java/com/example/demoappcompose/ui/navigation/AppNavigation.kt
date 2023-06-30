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
import com.example.demoappcompose.ui.auth.register.RegisterViewModel
import com.example.demoappcompose.ui.create_question.ChapterList
import com.example.demoappcompose.ui.create_question.CreateQuestion
import com.example.demoappcompose.ui.create_question.QuestionList
import com.example.demoappcompose.ui.dashboard.Dashboard
import com.example.demoappcompose.ui.dashboard.home.HomeViewModel
import com.example.demoappcompose.ui.dashboard.my_subscription.MySubscription
import com.example.demoappcompose.ui.dashboard.my_subscription.MySubscriptionViewModel
import com.example.demoappcompose.ui.paper_history.PaperHistory
import com.example.demoappcompose.ui.print_settings.PrintSettings
import com.example.demoappcompose.ui.profile.EditProfile
import com.example.demoappcompose.ui.profile.EditProfileViewModel
import com.example.demoappcompose.ui.register_purchase_book.RegisterToPurchaseBook
import com.example.demoappcompose.ui.splash.SplashScreenNew
import com.example.demoappcompose.ui.subject.SubjectScreen
import com.example.demoappcompose.ui.subject.SubjectsViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    isLoggedIn: Boolean,
    userId: String?
) {

    val startScreen: String =
        if (isLoggedIn) Screens.Dashboard.route + "/{userId}" else Screens.SplashScreen.route

    NavHost(navController = navController, startDestination = startScreen) {
        composable(route = Screens.SplashScreen.route) {
            SplashScreenNew(navController = navController)
        }

        composable(route = Screens.LoginScreen.route) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                navController = navController, loginViewModel = loginViewModel
            )
        }

        composable(route = Screens.RegisterScreen.route) {
            val registerViewModel = hiltViewModel<RegisterViewModel>()
            RegisterScreen(
                navController = navController, registerViewModel = registerViewModel
            )
        }

        composable(
            route = Screens.Dashboard.route + "/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.StringType
            })
        ) { entry ->

            val homeViewModel = hiltViewModel<HomeViewModel>()

            Dashboard(
                mainNavController = navController,
                homeViewModel = homeViewModel,
                userId = if (isLoggedIn) userId!! else entry.arguments?.getString("userId") ?: ""
            )
        }

        composable(
            route = Screens.SubjectScreen.route + "/{className}/{classId}/{isStream}",
            arguments = listOf(
                navArgument("className") {
                    type = NavType.StringType
                },
                navArgument("classId") {
                    type = NavType.StringType
                },
                navArgument("isStream") {
                    type = NavType.StringType
                }
            )
        ) { entry ->

            val subjectsViewModel = hiltViewModel<SubjectsViewModel>()

            SubjectScreen(
                navController = navController,
                subjectsViewModel = subjectsViewModel,
                className = entry.arguments?.getString("className") ?: "",
                classId = entry.arguments?.getString("classId") ?: "",
                isStream = entry.arguments?.getString("isStream") ?: ""
            )
        }

        composable(route = Screens.EditProfile.route) {
            val editProfileViewModel = hiltViewModel<EditProfileViewModel>()
            EditProfile(
                navController = navController,
                editProfileViewModel = editProfileViewModel
            )
        }

        composable(route = Screens.MySubscription.route) {
            val mySubscriptionViewModel = hiltViewModel<MySubscriptionViewModel>()
            MySubscription(
                navController = navController,
                mySubscriptionViewModel = mySubscriptionViewModel
            )
        }

        composable(route = Screens.RegisterPurchaseBook.route) {
            RegisterToPurchaseBook(navController = navController)
        }

        composable(route = Screens.PaperHistory.route) {
            PaperHistory(navController = navController)
        }

        composable(
            route = Screens.CreateQuestion.route + "/{subjectName}",
            arguments = listOf(navArgument("subjectName") {
                type = NavType.StringType
            })
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
            arguments = listOf(navArgument("subjectName") {
                type = NavType.StringType
            })
        ) { entry ->
            ChapterList(
                navController = navController,
                subjectName = entry.arguments?.getString("subjectName") ?: ""
            )
        }

        composable(
            route = Screens.QuestionList.route + "/{chapterName}",
            arguments = listOf(navArgument("chapterName") {
                type = NavType.StringType
            })
        ) { entry ->
            QuestionList(
                navController = navController,
                chapterName = entry.arguments?.getString("chapterName") ?: ""
            )
        }
    }
}
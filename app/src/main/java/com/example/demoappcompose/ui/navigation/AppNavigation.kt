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
import com.example.demoappcompose.ui.create_question.ChapterListViewModel
import com.example.demoappcompose.ui.create_question.CreateQuestion
import com.example.demoappcompose.ui.create_question.CreateQuestionViewModel
import com.example.demoappcompose.ui.create_question.QuestionList
import com.example.demoappcompose.ui.dashboard.Dashboard
import com.example.demoappcompose.ui.dashboard.home.HomeViewModel
import com.example.demoappcompose.ui.dashboard.my_subscription.MySubscription
import com.example.demoappcompose.ui.dashboard.my_subscription.MySubscriptionViewModel
import com.example.demoappcompose.ui.paper_history.PaperHistory
import com.example.demoappcompose.ui.paper_history.PaperHistoryViewModel
import com.example.demoappcompose.ui.print_settings.PrintSettings
import com.example.demoappcompose.ui.profile.EditProfile
import com.example.demoappcompose.ui.profile.EditProfileViewModel
import com.example.demoappcompose.ui.register_purchase_book.PurchaseBookViewModel
import com.example.demoappcompose.ui.register_purchase_book.RegisterToPurchaseBook
import com.example.demoappcompose.ui.splash.SplashScreen
import com.example.demoappcompose.ui.splash.SplashViewModel
import com.example.demoappcompose.ui.subject.SubjectScreen
import com.example.demoappcompose.ui.subject.SubjectsViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    isLoggedIn: Boolean,
    userId: String?,
    profilePicUrl: String?
) {

    val startScreen: String =
        if (isLoggedIn) Screens.Dashboard.route + "/{userId}/{profilePicUrl}" else Screens.SplashScreen.route

    NavHost(navController = navController, startDestination = startScreen) {

        composable(route = Screens.SplashScreen.route) {
            val splashViewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(
                navController = navController, splashViewModel = splashViewModel
            )
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

        composable(route = Screens.Dashboard.route + "/{userId}/{profilePicUrl}",
            arguments = listOf(navArgument("userId") {
                type = NavType.StringType
            }, navArgument("profilePicUrl") {
                type = NavType.StringType
            })) { entry ->

            val homeViewModel = hiltViewModel<HomeViewModel>()

            Dashboard(
                mainNavController = navController,
                homeViewModel = homeViewModel,
                userId = if (isLoggedIn) userId!! else entry.arguments?.getString("userId") ?: "",
                profilePicUrl = if (isLoggedIn) profilePicUrl!! else entry.arguments?.getString("profilePicUrl")
                    ?: ""
            )
        }

        composable(route = Screens.SubjectScreen.route + "/{className}/{classId}/{isStream}",
            arguments = listOf(navArgument("className") {
                type = NavType.StringType
            }, navArgument("classId") {
                type = NavType.StringType
            }, navArgument("isStream") {
                type = NavType.StringType
            })) { entry ->

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
                navController = navController, editProfileViewModel = editProfileViewModel
            )
        }

        composable(route = Screens.MySubscription.route) {
            val mySubscriptionViewModel = hiltViewModel<MySubscriptionViewModel>()
            MySubscription(
                navController = navController, mySubscriptionViewModel = mySubscriptionViewModel
            )
        }

        composable(route = Screens.RegisterPurchaseBook.route) {
            val purchaseBookViewModel = hiltViewModel<PurchaseBookViewModel>()
            RegisterToPurchaseBook(
                navController = navController, purchaseBookViewModel = purchaseBookViewModel
            )
        }

        composable(route = Screens.PaperHistory.route) {
            val paperHistoryViewModel = hiltViewModel<PaperHistoryViewModel>()
            PaperHistory(
                navController = navController, paperHistoryViewModel = paperHistoryViewModel
            )
        }

        composable(route = Screens.CreateQuestion.route + "/{classId}/{subjectId}/{subjectName}",
            arguments = listOf(navArgument("classId") {
                type = NavType.StringType
            }, navArgument("subjectId") {
                type = NavType.StringType
            }, navArgument("subjectName") {
                type = NavType.StringType
            })) { entry ->

            val createQuestionViewModel = hiltViewModel<CreateQuestionViewModel>()

            CreateQuestion(
                navController = navController,
                viewModel = createQuestionViewModel,
                classId = entry.arguments?.getString("classId") ?: "",
                subjectId = entry.arguments?.getString("subjectId") ?: "",
                subjectName = entry.arguments?.getString("subjectName") ?: ""
            )
        }

        composable(route = Screens.PrintSettings.route) {
            PrintSettings(navController = navController)
        }

        composable(
            route = Screens.ChapterList.route + "/{classId}/{subjectId}/{subjectName}",
            arguments = listOf(navArgument("classId") {
                type = NavType.StringType
            }, navArgument("subjectId") {
                type = NavType.StringType
            }, navArgument("subjectName") {
                type = NavType.StringType
            })) { entry ->

            val chapterListViewModel = hiltViewModel<ChapterListViewModel>()

            ChapterList(
                navController = navController,
                classId = entry.arguments?.getString("classId") ?: "",
                subjectId = entry.arguments?.getString("subjectId") ?: "",
                subjectName = entry.arguments?.getString("subjectName") ?: "",
                chapterListViewModel = chapterListViewModel
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
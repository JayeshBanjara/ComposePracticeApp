package com.example.demoappcompose.ui.navigation

sealed class Screens(val route: String) {
    object SplashScreen: Screens("splash_screen")
    object LoginScreen: Screens("login_screen")
    object RegisterScreen: Screens("register_screen")
    object Dashboard: Screens("dashboard")
    object SubjectScreen: Screens("subject")
    object EditProfile: Screens("edit_profile")
    object MySubscription: Screens("my_subscription")
    object RegisterPurchaseBook: Screens("register_purchase_book")
    object PaperHistory: Screens("paper_history")
    object CreateQuestion: Screens("create_question")
    object ChapterList: Screens("chapter_list")
    object QuestionList: Screens("question_list")
    object PrintSettings: Screens("print_settings")


    /**
     * Use this function to pass arguments to navigation destination
     */
    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

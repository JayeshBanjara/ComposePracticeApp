package com.example.demoappcompose.ui.navigation

sealed class Screens(val route: String) {
    object SplashScreen: Screens("splash_screen")
    object LoginScreen: Screens("login_screen")
    object RegisterScreen: Screens("register_screen")
    object Dashboard: Screens("dashboard")
    object SubjectScreen: Screens("subject")
    object EditProfile: Screens("edit_profile")

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

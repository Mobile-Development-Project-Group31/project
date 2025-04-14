package com.example.advancedandroidcourse.navigation

sealed class Screen(val route: String) {
    // Authentication Screens
    object Login : Screen("login")
    object Register : Screen("register")

    // Main App Screens
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Map : Screen("map")
    object Search : Screen("search")

    // Helper function for cleaner navigation
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
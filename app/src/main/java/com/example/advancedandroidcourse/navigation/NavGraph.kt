package com.example.advancedandroidcourse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.advancedandroidcourse.presentation.auth.LoginScreen
import com.example.advancedandroidcourse.presentation.auth.RegisterScreen
import com.example.advancedandroidcourse.presentation.main.HomeScreen
import com.example.advancedandroidcourse.presentation.main.MapScreen
import com.example.advancedandroidcourse.presentation.main.ProfileScreen
import com.example.advancedandroidcourse.presentation.main.SearchScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Authentication Screens
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }

        // Main App Screens
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }
        composable(Screen.Map.route) {
            MapScreen(navController)
        }
        composable(Screen.Search.route) {
            SearchScreen(navController)
        }
    }
}
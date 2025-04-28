package com.example.advancedandroidcourse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.advancedandroidcourse.presentation.auth.LoginScreen
import com.example.advancedandroidcourse.presentation.auth.RegisterScreen
import com.example.advancedandroidcourse.presentation.main.*
import com.google.firebase.auth.FirebaseAuth



@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.Map.route) { MapScreen(navController) }
        //composable(Screen.Search.route) { SearchScreen(navController) }
        composable(Screen.Restaurant.route) { RestaurantScreen(navController) }
        composable(Screen.Cart.route) { CartScreen(navController) }

        composable(
            route = Screen.OrderHistory.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType }) // tell the app that this screen expect userid from nav
        ) { backStackEntry -> // Gets the userId from the URL
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            OrderHistoryScreen(userId = userId) // Pass the userId to  OrderHistoryScreen
        }

        composable(
            route = Screen.Menu.route,
            arguments = listOf(navArgument("restaurantId") { type = NavType.StringType })
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getString("restaurantId")
            MenuScreen(navController, restaurantId)
        }


        navigation(
            startDestination = Screen.AddressAndPayment.route,
            route = "orderFlow"
        ) {
            composable(Screen.AddressAndPayment.route) {
                AddressAndPaymentScreen(navController)
            }
            composable(Screen.OrderConfirmation.route) {
                OrderConfirmationScreen(navController)
            }

        }
        }
    }


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
    object OrderHistory : Screen("order_history")

    // New com.example.advancedandroidcourse.data.model.Restaurant Flow Screens
    object Restaurant : Screen("restaurant")
    object Menu : Screen("menu/{restaurantId}") {
        fun createRoute(restaurantId: String): String = "menu/$restaurantId"
    }
    object Cart : Screen("cart")


    // Checkout flow
    object CheckoutFlow : Screen("checkout_flow")
    object AddressAndPayment : Screen("address_and_payment")
    object OrderConfirmation : Screen("order_confirmation")

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
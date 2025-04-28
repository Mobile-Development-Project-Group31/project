package com.example.advancedandroidcourse.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.advancedandroidcourse.presentation.viewmodel.OrderViewModel
import com.example.advancedandroidcourse.navigation.Screen

@SuppressLint("UnrememberedGetBackStackEntry")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderConfirmationScreen(navController: NavHostController) {
    // Get parent back stack entry for shared ViewModel
    val parentEntry = remember(navController) {
        //navController.getBackStackEntry("orderFlow")
        navController.getBackStackEntry(Screen.Home.route)
    }
    val orderViewModel: OrderViewModel = hiltViewModel(parentEntry)
    // Collect order details from ViewModel
    val orderDetails by orderViewModel.orderDetails.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Confirmed") }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "🎉 Thank you for your order!",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    // Display order details
                    Text("Address: ${orderDetails.address.mainAddress.ifBlank { "Not provided" }}")
                    Text("Building: ${orderDetails.address.buildingEntrance.ifBlank { "Not provided" }}")
                    Text("Doorbell Code: ${orderDetails.address.doorbellNumber.ifBlank { "Not provided" }}")
                    Text("Payment: ${orderDetails.paymentMethod}")
                    Text("Message for Rider: ${orderDetails.messageForRider.ifBlank { "No message" }}")
                }

                // Navigation button
                Button(
                    onClick = {
                        navController.navigate(Screen.Home.route) {
                            // Clear entire back stack including order flow
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Go Back to Home")
                }
            }
        }
    )
}
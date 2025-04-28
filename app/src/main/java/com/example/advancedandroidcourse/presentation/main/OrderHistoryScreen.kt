package com.example.advancedandroidcourse.presentation.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.example.advancedandroidcourse.data.model.Order


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(userId: String) {
    // A list to hold orders
    val orders = remember { mutableStateListOf<Order>() }

    // Firestore instance
    val db = FirebaseFirestore.getInstance()


    LaunchedEffect(userId) { // React if userId changes
        db.collection("orders")
            .whereEqualTo("userId", userId) // Only fetch current user's orders
            .get()
            .addOnSuccessListener { result ->
                orders.clear() // Clear old orders
                for (document in result) {
                    val order = document.toObject(Order::class.java) // Convert to Order
                    orders.add(order) // Add to list
                }
            }
            .addOnFailureListener { e ->
                Log.w("Firebase", "Error fetching orders", e)
            }
    }

    // UI
    Scaffold(
        topBar = {0
            TopAppBar(
                title = { Text("Your Orders") }
            )
        },
        content = { padding ->
            LazyColumn(
                contentPadding = padding,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(orders) { order ->
                    OrderCard(order)
                }
            }
        }
    )
}

@Composable
fun OrderCard(order: Order) { // Create card for each order.
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Order ID: ${order.orderId}", style = MaterialTheme.typography.titleMedium)
            Text("Status: ${order.orderStatus}", style = MaterialTheme.typography.bodyMedium)
            Text("Total: \$${order.totalAmount}", style = MaterialTheme.typography.bodyMedium)
            Text("Payment: ${order.paymentMethod}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

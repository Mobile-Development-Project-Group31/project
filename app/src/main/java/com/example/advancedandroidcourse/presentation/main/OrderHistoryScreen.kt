package com.example.advancedandroidcourse.presentation.main

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.google.firebase.firestore.FirebaseFirestore
import com.example.advancedandroidcourse.data.model.Order

@Composable
fun OrderHistoryScreen(userId: String) {
    // A list to hold orders
    val orders = remember { mutableStateListOf<Order>() }

    // Firestore instance
    val db = FirebaseFirestore.getInstance()

    // Fetch orders when the screen is loaded
    LaunchedEffect(true) {
        db.collection("orders")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                orders.clear() // Clear previous orders
                for (document in result) {
                    val order = document.toObject(Order::class.java)  // Convert Firestore document to Order object
                    orders.add(order)  // Add order to the list
                }
            }
            .addOnFailureListener { e ->
                Log.w("Firebase", "Error fetching orders", e)
            }
    }

    // Display orders in a LazyColumn (list)
    LazyColumn {
        items(orders) { order ->
            Text("Order ID: ${order.orderId}, Status: ${order.orderStatus}")
        }
    }
}

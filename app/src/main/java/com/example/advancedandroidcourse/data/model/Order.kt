package com.example.advancedandroidcourse.data.model

data class Order(
    val orderId: String = "",      // Order ID
    val orderStatus: String = "",  // Order Status (e.g., "Pending", "Completed")
    val totalAmount: Double = 0.0, // Total amount for the order
    val address: String = "",      // Delivery address
    val paymentMethod: String = "", // Payment method (e.g., "Credit Card")
    val userId: String = ""        // User ID who placed the order
)
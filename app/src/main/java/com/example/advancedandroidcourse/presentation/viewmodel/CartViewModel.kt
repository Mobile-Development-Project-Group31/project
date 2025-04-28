// presentation/viewmodel/CartViewModel.kt
package com.example.advancedandroidcourse.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.advancedandroidcourse.data.model.CartItem
import com.example.advancedandroidcourse.data.model.MenuItem
import com.example.advancedandroidcourse.data.model.Order
import com.example.advancedandroidcourse.data.model.OrderDetails
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {

    // Mutable state flow to hold cart items
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    // Calculate the total price of the items in the cart
    val totalPrice: Double
        get() = _cartItems.value.sumOf { it.price * it.quantity }
    // Convert Double to Float

    // Method to add an item to the cart
    fun addToCart(item: MenuItem) {
        val existingItem = _cartItems.value.find { it.id == item.id }
        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            _cartItems.value = _cartItems.value.map { if (it.id == item.id) updatedItem else it }
        } else {
            val newItem = CartItem(id = item.id, name = item.name, price = item.price, quantity = 1)
            _cartItems.value = _cartItems.value + newItem
        }
    }

    // Method to remove an item from the cart
    fun removeFromCart(item: CartItem) {
        _cartItems.value = _cartItems.value.filterNot { it.id == item.id }
    }

    // Method to clear the entire cart
    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun placeOrder(orderDetails: OrderDetails, userId: String, totalAmount: Double) { // Get the Firebase Firestore database
        val db = FirebaseFirestore.getInstance()

        Log.d("DEBUG", "Placing order with totalAmount: $totalAmount")
        val newOrder = Order( // Create a new Order object
            orderId = db.collection("orders").document().id, // Generate a unique ID for the order
            orderStatus = "Pending", // Initial status
            totalAmount = totalAmount,
            address = orderDetails.address.mainAddress, // you can customize
            paymentMethod = orderDetails.paymentMethod.name, // CARD or CASH
            userId = userId
        )

        db.collection("orders") // Save the new order into Firestore under "orders" collection
            .document(newOrder.orderId)
            .set(newOrder)
            .addOnSuccessListener { // Runs when save is successful
                Log.d("Firestore", "Order placed successfully")
                clearCart() // Clear the cart after placing order
            }
            .addOnFailureListener { e -> // Runs if there’s an error
                Log.e("Firestore", "Failed to place order: ${e.message}")
            }
    }
}

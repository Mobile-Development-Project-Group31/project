// presentation/viewmodel/CartViewModel.kt
package com.example.advancedandroidcourse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.advancedandroidcourse.data.model.CartItem
import com.example.advancedandroidcourse.data.model.MenuItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
}

package com.example.advancedandroidcourse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.advancedandroidcourse.data.model.Address
import com.example.advancedandroidcourse.data.model.OrderDetails
import com.example.advancedandroidcourse.data.model.PaymentMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor() : ViewModel() {

    // StateFlow to hold the order details
    private val _orderDetails = MutableStateFlow(
        OrderDetails(
            address = Address("", "", ""),
            paymentMethod = PaymentMethod.CARD, // Default to CARD
            messageForRider = ""
        )
    )
    val orderDetails: StateFlow<OrderDetails> = _orderDetails

    // Update address
    fun updateAddress(address: Address) {
        _orderDetails.value = _orderDetails.value.copy(address = address)
    }

    // Update payment method
    fun updatePaymentMethod(paymentMethod: PaymentMethod) {
        _orderDetails.value = _orderDetails.value.copy(paymentMethod = paymentMethod)
    }

    // Update message for rider
    fun updateRiderMessage(message: String) {
        _orderDetails.value = _orderDetails.value.copy(messageForRider = message)
    }

    // Set complete order details
    fun setOrderDetails(orderDetails: OrderDetails) {
        _orderDetails.value = orderDetails
    }
}

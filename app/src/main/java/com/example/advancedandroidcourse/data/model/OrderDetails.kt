package com.example.advancedandroidcourse.data.model


data class OrderDetails(
    val address: Address,
    val paymentMethod: PaymentMethod,
    val messageForRider: String = "",

)



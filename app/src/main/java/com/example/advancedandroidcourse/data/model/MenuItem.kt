package com.example.advancedandroidcourse.data.model

data class MenuItem(
    val id: String,
    val name: String,
    val price: Double,
    val description: String = ""
)
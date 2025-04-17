package com.example.advancedandroidcourse.data.model

data class Restaurant(
    val id: String,
    val name: String,
    val cuisine: String,
    val imageUrl: String = ""
)
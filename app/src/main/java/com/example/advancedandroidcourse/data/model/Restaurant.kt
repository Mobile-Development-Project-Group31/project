package com.example.advancedandroidcourse.data.model

data class Restaurant(
    val id: String,
    val name: String,
    val cuisine: String,
    val imageUrl: String = "",
    val menus: List<MenuItem> = emptyList(),
    val latitude: Double = 0.0,    // map
    val longitude: Double = 0.0    // map
)
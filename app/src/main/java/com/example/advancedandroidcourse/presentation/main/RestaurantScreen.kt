package com.example.advancedandroidcourse.presentation.main


import com.example.advancedandroidcourse.data.repository.FirestoreRepository


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.advancedandroidcourse.data.model.Restaurant
import com.example.advancedandroidcourse.navigation.Screen
import com.google.firebase.firestore.FirebaseFirestore


@Composable

fun RestaurantScreen(navController: NavHostController) {
    val repository = FirestoreRepository(FirebaseFirestore.getInstance()) // // added to firestore
    val restaurantList = remember { mutableStateOf<List<Restaurant>>(emptyList()) } // // added to firestore

    LaunchedEffect(true) { // added to firestore
        restaurantList.value = repository.getRestaurants()
    }

    val restaurants = restaurantList.value

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item { Text("Restaurants", style = MaterialTheme.typography.headlineLarge) }

        items(restaurants) { restaurant ->
            RestaurantCard(restaurant = restaurant, navController = navController)
        }
    }
}

@Composable
private fun RestaurantCard(restaurant: Restaurant, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate(Screen.Menu.createRoute(restaurant.id))
                // Passing the restaurant.id to MenuScreen
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(restaurant.name, style = MaterialTheme.typography.titleLarge)
            Text(restaurant.cuisine, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

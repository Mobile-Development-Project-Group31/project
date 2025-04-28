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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.advancedandroidcourse.data.model.Restaurant
import com.example.advancedandroidcourse.navigation.Screen
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RestaurantScreen(navController: NavHostController) {
    val repository = remember { FirestoreRepository(FirebaseFirestore.getInstance()) }
    val restaurantList = remember { mutableStateOf<List<Restaurant>>(emptyList()) }
    val selectedCategory = remember { mutableStateOf<String?>(null) }
    val searchQuery =
        remember { mutableStateOf("") } // variable to remember the search text typed by user
    val filteredRestaurants =
        restaurantList.value.filter { restaurant -> // create new list. go through every restaurant
            val matchesCategory =
                selectedCategory.value == null || restaurant.cuisine == selectedCategory.value  //If no category is selected, it matches everything. If a category is selected, it only matches restaurants of that cuisine.
            val matchesSearch = searchQuery.value.isBlank() || restaurant.name.contains(
                searchQuery.value,
                ignoreCase = true
            )  //If the search bar is empty, match everything.  Otherwise, check if the restaurant's name contains the search text
            matchesCategory && matchesSearch // Only restaurants that match both category and search are shown.
        }

    LaunchedEffect(true) {
        restaurantList.value = repository.getRestaurants()
    }

    /*val filteredRestaurants = restaurantList.value.filter { restaurant ->
        selectedCategory.value == null || restaurant.cuisine == selectedCategory.value
    }*/

    val categories = listOf("American", "Italian", "Japanese")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField( // create search bar
            value = searchQuery.value,
            onValueChange = { newText ->
                searchQuery.value = newText
            },
            placeholder = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Category",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp) // Fix height for all buttons for clean view
                ) {
                    Button(
                        onClick = { selectedCategory.value = category },
                        modifier = Modifier
                            .fillMaxSize() // Fill the Box completely
                    ) {
                        Text(category, fontSize = 11.sp)
                    }
                }
            }
        }

        Text(
            "Restaurants",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredRestaurants) { restaurant ->
                RestaurantCard(restaurant = restaurant, navController = navController)
            }
        }
    }
}

@Composable
private fun RestaurantCard(restaurant: Restaurant, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screen.Menu.createRoute(restaurant.id))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(restaurant.name, style = MaterialTheme.typography.titleLarge)
            Text(restaurant.cuisine, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

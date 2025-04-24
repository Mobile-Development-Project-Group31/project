package com.example.advancedandroidcourse.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.advancedandroidcourse.data.model.MenuItem
import com.example.advancedandroidcourse.navigation.Screen
import com.example.advancedandroidcourse.presentation.viewmodel.CartViewModel

import androidx.compose.runtime.*
import com.example.advancedandroidcourse.data.repository.FirestoreRepository
import com.google.firebase.firestore.FirebaseFirestore


@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun MenuScreen(navController: NavHostController, restaurantId: String?) {
    if (restaurantId == null) {
        Text("Error: No restaurant ID provided")
        return
    }

    /*
    // Use shared CartViewModel scoped to the root nav graph (startDestination: Screen.Login.route)
    val parentEntry = remember {
        navController.getBackStackEntry(Screen.Login.route)
    }
    val viewModel: CartViewModel = hiltViewModel(parentEntry)

    val menuItems = remember {
        listOf(
            MenuItem("1", "Cheeseburger", 9.99, "Classic beef burger"),
            MenuItem("2", "Fries", 4.99, "Crispy potato fries"),
            MenuItem("3", "Milkshake", 5.99, "Vanilla milkshake")
        )
    }*/

    val parentEntry = remember {
        navController.getBackStackEntry(Screen.Login.route)
    }
    val viewModel: CartViewModel = hiltViewModel(parentEntry)

    // Firestore
    val repository = remember { FirestoreRepository(FirebaseFirestore.getInstance()) }
    // Mutable state to store menu items
    val menuItems = remember { mutableStateOf<List<MenuItem>>(emptyList()) }

    // Load data when screen appears
    LaunchedEffect(true) {
        val restaurants = repository.getRestaurants()
        val restaurant = restaurants.find { it.id == restaurantId }
        menuItems.value = restaurant?.menus ?: emptyList()
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate(Screen.Cart.route) },
                icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart") },
                text = { Text("View Cart") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            item {
                Text(
                    //"Menu for Restaurant $restaurantId",
                    "Menu for Restaurant",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }

            items(menuItems.value) { item ->
                MenuItemCard(item = item, viewModel = viewModel)
            }
        }
    }
}

@Composable
private fun MenuItemCard(item: MenuItem, viewModel: CartViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, style = MaterialTheme.typography.titleLarge)
                Text("$${"%.2f".format(item.price)}", style = MaterialTheme.typography.bodyLarge)
                Text(item.description, style = MaterialTheme.typography.bodyMedium)
            }
            FilledTonalButton(onClick = {
                viewModel.addToCart(item)
            }) {
                Text("Add to Cart")
            }
        }
    }
}
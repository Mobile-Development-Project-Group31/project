package com.example.advancedandroidcourse.presentation.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.advancedandroidcourse.data.model.CartItem
import com.example.advancedandroidcourse.navigation.Screen
import com.example.advancedandroidcourse.presentation.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavHostController) {
    // Use shared ViewModel scoped to a common parent route (Login in this case)
    val parentEntry = remember(navController.currentBackStackEntryAsState().value) {
        navController.getBackStackEntry(Screen.Home.route)
    }
    val viewModel: CartViewModel = hiltViewModel(parentEntry)
    val cartItems by viewModel.cartItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Cart") },
                actions = {
                    IconButton(onClick = { viewModel.clearCart() }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Clear Cart"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Your cart is empty")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(cartItems) { item ->
                        CartItemRow(item = item, viewModel = viewModel)
                        Log.d("CartScreen", "Current cart items: $cartItems")
                    }
                }
                TotalSection(viewModel = viewModel, navController = navController)
            }
        }
    }
}

@Composable
private fun CartItemRow(item: CartItem, viewModel: CartViewModel) {
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
                Text(item.name, style = MaterialTheme.typography.titleMedium)
                Text("Quantity: ${item.quantity}")
                Text("Price: $${"%.2f".format(item.totalPrice)}")
            }
            IconButton(onClick = { viewModel.removeFromCart(item) }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Remove item"
                )
            }
        }
    }
}
@Composable
private fun TotalSection(viewModel: CartViewModel, navController: NavHostController) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total:", style = MaterialTheme.typography.titleLarge)
            Text(
                "$${"%.2f".format(viewModel.totalPrice)}",
                style = MaterialTheme.typography.titleLarge
            )
        }
        Button(
            onClick = {
                // Instead of popping the back stack, navigate to the Address and Payment screen
                navController.navigate(Screen.AddressAndPayment.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Place Order")
        }
    }
}


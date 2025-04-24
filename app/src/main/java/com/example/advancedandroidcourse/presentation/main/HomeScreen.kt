package com.example.advancedandroidcourse.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.advancedandroidcourse.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    // Drawer state and coroutine scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Menu items for navigation drawer
    val menuItems = listOf(
        Screen.Home,
        Screen.Profile,
        Screen.Map,
        Screen.Search,
        Screen.OrderHistory // Adding OrderHistory to the navigation drawer
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Food Delivery",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
                HorizontalDivider()

                // Drawer items
                menuItems.forEach { screen ->
                    NavigationDrawerItem(
                        label = { Text(screen.route) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(screen.route) {
                                popUpTo(Screen.Home.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Home") },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Text("≡") // Simple menu icon using text
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome to Our App!",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Home screen content will go here",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Button(
                    onClick = { /* Add your action here */ },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Get Started")
                }
                Button(onClick = { navController.navigate(Screen.Restaurant.route) }) {
                    Text("Browse Restaurants")
                }
            }
        }
    }
}

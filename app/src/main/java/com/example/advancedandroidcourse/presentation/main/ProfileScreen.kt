package com.example.advancedandroidcourse.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.advancedandroidcourse.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val notificationsEnabled = remember { mutableStateOf(true) }
    val darkModeEnabled = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = "User Name",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "user@example.com",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

                Button(
                    onClick = { /* Edit profile */ },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Text(" Edit Profile", modifier = Modifier.padding(start = 4.dp))
                }
            }

            // Settings Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Notification Toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text("Notifications")
                        }
                        Switch(
                            checked = notificationsEnabled.value,
                            onCheckedChange = { notificationsEnabled.value = it }
                        )
                    }

                    // Dark Mode Toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = "Dark Mode",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text("Dark Mode")
                        }
                        Switch(
                            checked = darkModeEnabled.value,
                            onCheckedChange = { darkModeEnabled.value = it }
                        )
                    }
                }
            }

            // Account Actions
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        // Sign out of Firebase
                        FirebaseAuth.getInstance().signOut()

                        // Navigate back to Login, clearing the back stack
                        navController.navigate(Screen.Login.route) {
                            // Pop everything up to the start destination (usually Login)
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            // Avoid multiple copies of Login on the stack
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sign Out")
                }
            }
        }
    }
}
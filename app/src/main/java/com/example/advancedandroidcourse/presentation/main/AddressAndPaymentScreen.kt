package com.example.advancedandroidcourse.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.advancedandroidcourse.data.model.Address
import com.example.advancedandroidcourse.data.model.PaymentMethod
import com.example.advancedandroidcourse.navigation.Screen
import com.example.advancedandroidcourse.presentation.viewmodel.OrderViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressAndPaymentScreen(navController: NavHostController) {
    val parentEntry = remember(navController) {
        navController.getBackStackEntry("orderFlow")
    }
    // Retrieve ViewModel scoped to the parent navigation route
    val orderViewModel: OrderViewModel = hiltViewModel(parentEntry)

    // Local states for user inputs
    var mainAddress by remember { mutableStateOf("") }
    var buildingEntrance by remember { mutableStateOf("") }
    var doorbellNumber by remember { mutableStateOf("") }
    var selectedPaymentMethod by remember { mutableStateOf<PaymentMethod?>(null) }
    var messageForRider by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Address & Payment") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Enter your address", style = MaterialTheme.typography.titleMedium)

                // Main Address Field
                OutlinedTextField(
                    value = mainAddress,
                    onValueChange = {
                        mainAddress = it
                        orderViewModel.updateAddress(Address(it, buildingEntrance, doorbellNumber))
                    },
                    label = { Text("Main Address") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Building Entrance Field
                OutlinedTextField(
                    value = buildingEntrance,
                    onValueChange = {
                        buildingEntrance = it
                        orderViewModel.updateAddress(Address(mainAddress, it, doorbellNumber))
                    },
                    label = { Text("Building Entrance") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Doorbell Number Field
                OutlinedTextField(
                    value = doorbellNumber,
                    onValueChange = {
                        doorbellNumber = it
                        orderViewModel.updateAddress(Address(mainAddress, buildingEntrance, it))
                    },
                    label = { Text("Doorbell Number / Code") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                // Payment Method Section
                Text("Select Payment Method", style = MaterialTheme.typography.titleMedium)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedPaymentMethod == PaymentMethod.CARD,
                        onClick = {
                            selectedPaymentMethod = PaymentMethod.CARD
                            orderViewModel.updatePaymentMethod(PaymentMethod.CARD)
                        }
                    )
                    Text("Card")
                    Spacer(modifier = Modifier.width(16.dp))
                    RadioButton(
                        selected = selectedPaymentMethod == PaymentMethod.CASH,
                        onClick = {
                            selectedPaymentMethod = PaymentMethod.CASH
                            orderViewModel.updatePaymentMethod(PaymentMethod.CASH)
                        }
                    )
                    Text("Cash")
                }

                // Message for Rider Field
                OutlinedTextField(
                    value = messageForRider,
                    onValueChange = {
                        messageForRider = it
                        orderViewModel.updateRiderMessage(it)
                    },
                    label = { Text("Message for the rider") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }

            // Continue to Confirmation Button
            Button(
                onClick = {



                    // Navigate to confirmation screen
                    navController.navigate(Screen.OrderConfirmation.route)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = mainAddress.isNotBlank() && selectedPaymentMethod != null
            ) {
                Text("Continue to Confirm Order")
            }
        }
    }
}

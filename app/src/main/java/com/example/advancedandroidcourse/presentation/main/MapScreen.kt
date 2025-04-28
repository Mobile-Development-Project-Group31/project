package com.example.advancedandroidcourse.presentation.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.advancedandroidcourse.data.model.Restaurant
import com.example.advancedandroidcourse.data.repository.FirestoreRepository

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.maps.android.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@SuppressLint("MissingPermission") // handle permission manually
@Composable
fun MapScreen(navController: NavController) {

    val repository = remember { FirestoreRepository(FirebaseFirestore.getInstance()) }
    val restaurantList = remember { mutableStateOf<List<Restaurant>>(emptyList()) }

    LaunchedEffect(Unit) {
        restaurantList.value = repository.getRestaurants()
    }

    val context = LocalContext.current

    // initial  Helsinki
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(60.1695, 24.9354), 12f)
    }

    // Check if location permission is granted
    val hasPermission = ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    // Map UI
    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap( //
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = hasPermission),
            uiSettings = MapUiSettings(myLocationButtonEnabled = true)
        ) {
            restaurantList.value.forEach { restaurant ->
                Marker(
                    state = MarkerState(position = LatLng(restaurant.latitude, restaurant.longitude)),
                    title = restaurant.name
                )
            }
        }
    }
}
package com.example.advancedandroidcourse.data.repository

import android.util.Log
import com.example.advancedandroidcourse.data.model.MenuItem
import com.example.advancedandroidcourse.data.model.Restaurant
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository(private val db: FirebaseFirestore) {

    suspend fun getRestaurants(): List<Restaurant> {
        return try {
            val snapshot = db.collection("restaurants").get().await() //
                snapshot.documents.mapNotNull { doc ->
                val id = doc.id
                val name = doc.getString("name") ?: return@mapNotNull null
                val cuisine = doc.getString("cuisine") ?: ""
                val imageUrl = doc.getString("imageUrl") ?: ""

                val latitude = (doc.get("latitude") as? Number)?.toDouble() ?: 0.0
                val longitude = (doc.get("longitude") as? Number)?.toDouble() ?: 0.0

                val menus = (doc["menus"] as? List<Map<String, Any>>)?.mapNotNull { menuMap -> //
                    val menuId = menuMap["id"] as? String ?: return@mapNotNull null
                    val menuName = menuMap["name"] as? String ?: return@mapNotNull null
                    val menuPrice = (menuMap["price"] as? Number)?.toDouble() ?: 0.0
                    val description = menuMap["description"] as? String ?: ""
                    MenuItem(menuId, menuName, menuPrice, description)
                } ?: emptyList()

                Restaurant(id, name, cuisine, imageUrl, menus, latitude, longitude)
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Error loading restaurants: ${e.message}")
            emptyList()
        }
    }
}
package com.example.advancedandroidcourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.advancedandroidcourse.navigation.AppNavHost
import com.example.advancedandroidcourse.navigation.Screen
import com.example.advancedandroidcourse.ui.theme.AdvancedAndroidCourseTheme
import dagger.hilt.android.AndroidEntryPoint
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val auth = FirebaseAuth.getInstance()
        val startDestination = if (auth.currentUser != null) {

            Screen.Home.route
        } else {

            Screen.Login.route
        }
        setContent {
            AdvancedAndroidCourseTheme {
                val navController = rememberNavController()

                AppNavHost(
                    navController = navController,
                    startDestination = startDestination
                )
            }
        }
    }
}
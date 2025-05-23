package com.example.wholesaler_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.wholesaler_app.ui.theme.Wholesaler_appTheme
import com.example.wholesaler_app.presentation.home.HomeScreen

class MainActivity : ComponentActivity() {



    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enables edge-to-edge display
        setContent {
            Wholesaler_appTheme {
                // Scaffold with CustomTopBar, no Greeting
                HomeScreen()
            }
        }
    }
}

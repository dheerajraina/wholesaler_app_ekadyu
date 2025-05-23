package com.example.wholesaler_app.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun HomeTopBar(navController:NavController,unreadNotifications:Int, unreadMessages:Int, screenWidth: Dp, screenHeight: Dp,) {

    val density = LocalDensity.current.density
    val appBarHeight = (40 * density).dp // Dynamically calculate app bar height
    Surface(
        tonalElevation = 4.dp,
        color =  Color(134,174,95)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(appBarHeight) // Standard height for top app bar
                .padding(horizontal = 8.dp) // Horizontal padding for icons
                .windowInsetsPadding(WindowInsets.statusBars), // Add status bar padding
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 🔹 Left-side icons (navigation area)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {navController.navigate("trends")}) {
                    Icon(Icons.Default.BarChart, contentDescription = "Stats")
                }


                Box(
                    modifier = Modifier
                        .padding(end = 8.dp) // Space between icons
                ){
                    IconButton(onClick = { /* Handle favorite action */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }


                    if(unreadNotifications>0){
                        Badge(
                            containerColor = Color(130, 241, 18),
                            modifier = Modifier
                                .align(Alignment.TopEnd) // Position the badge in the top-right corner of the icon
                                .size(20.dp),
                            content = {
                                Text(
                                    text = unreadNotifications.toString(),
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        )

                    }

                }

            }

            // 🔸 Centered title (takes up remaining space)
            Text(
                text = "Go4U",
                modifier = Modifier
                    .weight(2f) // Ensures the title is centered
                    .padding(horizontal = (density * 28).dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.W900
            )

            // 🔹 Right-side icons (actions)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(

                    modifier = Modifier
                        .padding(end = 8.dp) // Space between icons
                ){
                    IconButton(onClick = { /* Handle search action */ }) {
                        Icon(Icons.Filled.ChatBubble, contentDescription = "Messages")
                    }


                    if(unreadMessages>0){
                        Badge(
                            containerColor = Color(130, 241, 18),
                            modifier = Modifier
                                .align(Alignment.TopEnd) // Position the badge in the top-right corner of the icon
                                .size(20.dp),
                            content = {
                                Text(
                                    text = unreadMessages.toString(),
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        )

                    }

                }

                IconButton(onClick = { /* Handle more options */ }) {
                    Icon(Icons.Filled.AccountCircle, contentDescription = "Account")
                }

            }
        }
    }
}

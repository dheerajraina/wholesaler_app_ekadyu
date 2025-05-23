package com.example.wholesaler_app.presentation.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HomeSearchBar(
//    onSearch: (String) -> Unit
    screenWidth: Dp, screenHeight: Dp
) {
    var query by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = query,
            onValueChange = {query=it},
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.LightGray,       // Background color
                focusedTextColor = Color.Black,                // Input text color
                focusedPlaceholderColor = Color.DarkGray,
                unfocusedContainerColor = Color.LightGray,
                unfocusedPlaceholderColor = Color.DarkGray,
                unfocusedTrailingIconColor = Color.DarkGray,
                focusedTrailingIconColor = Color.DarkGray,
            ),
            placeholder = {
                Text(
                    text = "Search...",
                    fontSize = 14.sp
                )
            },
            shape = RoundedCornerShape(20,20,20,20),
            trailingIcon = { Icon(Icons.Filled.Search, contentDescription = "Account") },


            )

    }
}

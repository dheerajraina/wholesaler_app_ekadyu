package com.example.wholesaler_app.presentation.common.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

//Rounded Button

@Composable
fun ButtonOne(onClick:()->Unit,screenHeight:Dp,screenWidth: Dp){
    Button(
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(134,174,95)),
        onClick = onClick ,
        modifier = Modifier
//                    .fillMaxWidth()
            .padding(vertical = screenHeight * 0.05f, horizontal = screenWidth * 0.3f)
            .size(width = 150.dp, height = 70.dp) // Smaller and more square-like

    ) {
        Text(
//                    textAlign = TextAlign.Center,
            text = "Load more",
            color = Color.White,
            fontWeight = FontWeight.W900
        )
    }
}
package com.example.wholesaler_app.presentation.home.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.wholesaler_app.presentation.common.components.ButtonOne
import com.example.wholesaler_app.presentation.common.components.CardOne


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListWithShowMore(
    screenWidth: Dp, screenHeight: Dp
) {



    val initialItemCount = 5
    var itemCount by remember { mutableStateOf(initialItemCount) }

    val items = List(itemCount) { "Item #${it + 1}" }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(items) { item ->
            CardOne(item)
        }

        // Button as the last item in the list
        item {
            ButtonOne(onClick = {itemCount += 5 },screenHeight,screenWidth)
        }
    }
}

package com.example.wholesaler_app.presentation.pastorders


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wholesaler_app.domain.repository.OrderRepositoryImpl
import com.example.wholesaler_app.presentation.myorders.MyOrdersViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PastOrdersScreen(navController: NavController, screenWidth:Dp, screenHeight:Dp) {

//    val viewModel =remember
//    val orders by viewModel {  }

    val viewModel = remember { PastOrdersViewModel(OrderRepositoryImpl()) }
    val completedOrders by viewModel.completedOrders.collectAsState()
//    val isLoading by viewModel.isLoading.collectAsState()
//    val hasError by viewModel.hasError.collectAsState()




    var selectedFilter by remember { mutableStateOf("All Orders") }
    val filters = listOf("All Orders", "1 day ago", "2 days ago", "3 days ago", "4 days ago", "Custom search")
    var isLoading by remember { mutableStateOf(false) }
    var hasError by remember { mutableStateOf(false) }
//    var completedOrders by remember { mutableStateOf(sampleOrders) }
    var selectedOrderId by remember { mutableStateOf<Int?>(null) }
    var showFilterDialog by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color.White, Color(0xFFEEF2F3))
                )
            )
    ) {

        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ),
            title = {
                Text(
                    "Orders Completed",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back", tint = Color.DarkGray)
                }
            }
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.FilterList, contentDescription = null, tint = Color(0xFF96EB55))
            Spacer(Modifier.width(8.dp))
            Text("Filter Orders", fontWeight = FontWeight.Medium)
            Spacer(Modifier.width(16.dp))
            var expanded by remember { mutableStateOf(false) }
            Box {
                OutlinedButton(
                    onClick = { expanded = true },
                    enabled = !isLoading
                ) {
                    Text(selectedFilter)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    filters.forEach { filter ->
                        DropdownMenuItem(
                            text = { Text(filter)},
                            onClick = {
                            selectedFilter = filter
                            expanded = false
//                            TODO:- API Call
                        })
                    }
                }
            }
            if (selectedFilter != "All Orders") {
                IconButton(onClick = { selectedFilter = "All Orders" }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear Filter")
                }
            }
        }


        if (isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Spacer(Modifier.height(8.dp))
                Text("Loading orders...")
            }
        }

        else if (hasError) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.Error, contentDescription = null, tint = Color.Red, modifier = Modifier.size(40.dp))
                Spacer(Modifier.height(8.dp))
                Text("Failed to load orders", color = Color.Red)
                Spacer(Modifier.height(8.dp))
                OutlinedButton(onClick = { /* Retry logic */ }) {
                    Text("Try Again")
                }
            }
        }

        else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
            ) {
                if (completedOrders.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No completed orders found")
                        }
                    }
                } else {
                    items(completedOrders.size) { order ->
                        Card(
                            modifier = Modifier
//                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 8.dp)
                                .clickable {
                                    selectedOrderId = if (selectedOrderId == completedOrders[order].order_id) null else completedOrders[order].order_id
                                },
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text="Order #${completedOrders[order].order_id}",
                                    fontWeight = FontWeight.Bold,
                                )
                                Text(
                                    "Total: ₹${completedOrders[order].total_order_amount}",
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF96EB55)
                                )
                                if (selectedOrderId == completedOrders[order].order_id) {
                                    Spacer(Modifier.height(8.dp))
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color(0xFFF7F7F7))
                                            .padding(8.dp)
                                    ) {
                                        completedOrders[order].order_items.forEach { item ->
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 4.dp)
                                            ) {
                                                Text(item.product_name, fontWeight = FontWeight.SemiBold)
                                                Text("Quantity: ${item.quantity} ${item.unit_name}")
                                                Text("Max Price: ₹${item.max_item_price}")
                                            }
                                            HorizontalDivider()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
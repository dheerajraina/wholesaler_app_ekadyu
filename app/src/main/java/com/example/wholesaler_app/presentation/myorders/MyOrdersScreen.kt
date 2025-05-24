package com.example.wholesaler_app.presentation.myorders

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import com.example.wholesaler_app.domain.repository.OrderRepositoryImpl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOrdersScreen(navController: NavController, screenWidth: Dp, screenHeight: Dp) {
    val tabTitles = listOf("All Orders", "Price ↑", "Price ↓", "Bulk Orders","By Product")
    var selectedTabIndex by remember { mutableStateOf(0) }

    val viewModel = remember { MyOrdersViewModel(OrderRepositoryImpl()) }
    val orders by viewModel.orders.collectAsState()


    val dummyItems = listOf(
        "Item1: Carrot - 25 kg (Rs. 10/kg)",
        "Item2: Potato - 10 kg (Rs. 12/kg)"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "My Orders")
                    }
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                },
                actions = { Icon(Icons.Default.Search,"Search") }

            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
            // Scrollable sliding tab row
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                edgePadding = 16.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = MaterialTheme.colorScheme.primary,
                        height = 3.dp
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            LazyColumn {
                items(orders.size) { index ->
                    val order = orders[index]
                    OrderCard(
                        orderTitle = "Order #${order.id}",
                        items = order.items,
                        totalAmount = "${order.total}"

                    ) {
//                        TODO: navigation on view details click
                    }
                }
            }

        }
    }
}





@Composable
fun OrderCard(
    orderTitle: String,
    items: List<String>,
    totalAmount: String,
    onViewDetailsClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Top row: Order title and three-dot menu
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = orderTitle,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Menu",
                        tint = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Items
                items.forEach { itemText ->
                    Text(text = itemText, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Bottom row: Total amount and View Details button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "Total Amount",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.DarkGray
                            )
                        Text(
                            text = totalAmount,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(134,174,95)
                            )
                    }

                    Button(
                        onClick = onViewDetailsClick,
                        shape = RoundedCornerShape(4.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(134,174,95)),
                        modifier = Modifier
                            .defaultMinSize(minHeight = 36.dp)
                    ) {
                        Text(
                            text = "View Details",
                            fontSize = 12.sp,
                            color = Color.White
                            )
                    }
                }
            }
        }
    }
}




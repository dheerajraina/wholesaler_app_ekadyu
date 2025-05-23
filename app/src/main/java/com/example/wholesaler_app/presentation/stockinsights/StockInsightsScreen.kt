package com.example.wholesaler_app.presentation.stockinsights



import android.R
import android.content.Context
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.AvTimer
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockInsightsScreen(navController: NavController, screenWidth: Dp, screenHeight: Dp) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(end = screenWidth * 0.15f),
                            text = "Trends",
                            fontWeight = FontWeight.W900,
                        )
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
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {

            // Fixed Tab Bar
            val tabs = listOf("Stock Levels", "Low Stocks", "Slow Moving")
            var selectedTabIndex by remember { mutableStateOf(0) }

            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White,
                contentColor = Color.Black
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            // Scrollable content area
            Box(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
            ) {
                when (selectedTabIndex) {
                    0 -> StockLevelsContent()
                    1 -> LowStocksContent()
                    2 -> SlowMovingContent()
                }
            }
        }
    }
}




@Composable
fun StockInsightsTabBar() {
    val tabs = listOf("Stock Levels ", "Low Stocks","Slow Moving")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White,
            contentColor = Color.Black
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }


        when (selectedTabIndex) {
            0 -> StockLevelsContent()
            1 -> LowStocksContent()
            2-> SlowMovingContent()
        }
    }
}

@Composable
fun StockLevelsContent() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
//            .background(Color(0xFFD0F0C0)), // light green
        contentAlignment = Alignment.Center
    ) {
//        Text("Sales Trends Page", style = MaterialTheme.typography.headlineMedium)
        Column {
            WarehouseDropdownMenu()
            Spacer(Modifier.height(30.dp))
            StockLevelsChart()
        }
    }

}




@Composable
fun LowStocksContent() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
//            .background(Color(0xFFFFE4B5)),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 20.dp),
            ){
                Icon(Icons.Outlined.Warning,"Alert", tint = Color.Red)
                Text(
                    "Low Stock Alerts",
                    style= MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )


            }
            LowStockCard(
                productName = "Tomatoes",
                totalStock = 235f,
                mandiData = listOf(
                    "Azadpur Mandi" to 80f,
                    "Koyambedu Mandi" to 65f,
                    "Vashi Mandi" to 90f
                )
            )

        }
    }
}

@Composable
fun SlowMovingContent() {

    data class SlowMovingStockItem(
        val productName: String,
        val currentStock: Float,
        val location: String,
        val weeklySales: Float,
        val daysInStock: Int
    )

    val stockItems = listOf(
        SlowMovingStockItem("Tomatoes", 80f, "Koyambedu Mandi", 61.7f, 34),
        SlowMovingStockItem("Onions", 120f, "Vashi Mandi", 70.2f, 21),
        SlowMovingStockItem("Potatoes", 95f, "Azadpur Mandi", 55.3f, 45),
        SlowMovingStockItem("Carrots", 60f, "Mumbai Mandi", 40.0f, 18),
        SlowMovingStockItem("Chilies", 30f, "Howrah Mandi", 25.5f, 52)
    )
    Box(
        modifier = Modifier
            .fillMaxSize(),
//            .background(Color(0xFFFFE4B5)),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {

            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 20.dp),
            ){
                Icon(Icons.Outlined.AvTimer,"Alert", tint = Color.Cyan)
                Text(
                    "Slow Moving Products",
                    style= MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )


            }

            stockItems.forEach { item ->
                SlowMovingStockCard(
                    productName = item.productName,
                    currentStock = item.currentStock,
                    location = item.location,
                    weeklySales = item.weeklySales,
                    daysInStock = item.daysInStock
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarehouseDropdownMenu(
    onPeriodSelected: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Select Warehouse/Mandi") }

    val options = listOf("Koyambedu Mandi", "Alice Mandi", "Updated Mandi")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedOption = selectionOption
                        expanded = false
                        onPeriodSelected(selectionOption) // trigger your callback
                    }
                )
            }
        }
    }
}




@Composable
fun StockLevelsChart(context: Context = LocalContext.current) {
    val products = listOf("Tomatoes", "Potatoes", "Onions", "Carrots", "Beans", "Chilies")
    val stockQuantities = listOf(2000f, 850f, 950f, 400f, 730f, 620f) // quantities in kg

    val productColors = listOf(
        Color(0xFFE53935), // Red
        Color(0xFFFFB300), // Amber
        Color(0xFF8E24AA), // Purple
        Color(0xFF43A047), // Green
        Color(0xFF1E88E5), // Blue
        Color(0xFFFB8C00)  // Orange
    )

    var expanded by remember { mutableStateOf(false) }
    var chartRef by remember { mutableStateOf<BarChart?>(null) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Stock Levels - Koyambedu Mandi", style = MaterialTheme.typography.titleMedium)

                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Export")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Download PNG") },
                            onClick = {
                                expanded = false
                                chartRef?.let {
                                    it.saveToGallery("stock_levels_chart", 100)
                                    Toast.makeText(context, "Saved PNG to gallery", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Download CSV") },
                            onClick = { expanded = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Download SVG") },
                            onClick = {
                                expanded = false
                                Toast.makeText(context, "Saved to gallery", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AndroidView(
                factory = { ctx ->
                    BarChart(ctx).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            500
                        )
                        description.isEnabled = false
                        setTouchEnabled(true)
                        setScaleEnabled(true)

                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            granularity = 1f
                            valueFormatter = IndexAxisValueFormatter(products)
                            textColor = Color.Black.toArgb()
                            setDrawGridLines(false)
                        }

                        axisLeft.apply {
                            axisMinimum = 0f
                            valueFormatter = object : ValueFormatter() {
                                override fun getFormattedValue(value: Float): String {
                                    return "${value.toInt()} kg"
                                }
                            }
                            textColor = Color(0xFF4CAF50).toArgb()
                        }

                        axisRight.isEnabled = false

                        val entries = stockQuantities.mapIndexed { index, quantity ->
                            BarEntry(index.toFloat(), quantity)
                        }

                        val dataSet = BarDataSet(entries, "Available Stock (kg)").apply {
                            setColors(productColors.map { it.toArgb() })
                            setDrawValues(true)
                            valueTextSize = 12f
                        }

                        data = BarData(dataSet).apply {
                            barWidth = 0.9f
                        }

                        setFitBars(true)
                        invalidate()

                        chartRef = this
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }
    }
}




@Composable
fun LowStockCard(
    productName: String = "Product 1",
    totalStock: Float = 80f,
    mandiData: List<Pair<String, Float>> = listOf(
        "Azadpur Mandi" to 50f,
        "Koyambedu Mandi" to 20f,
        "Vashi Mandi" to 10f
    )
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = productName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .border(2.dp, Color.Red, shape = CircleShape)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        "Total: ${totalStock.toInt()} kg",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Red

                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Mandi Rows
            mandiData.forEach { (mandiName, quantity) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal =  10.dp),
                    ) {
                        Row {
                            Icon(Icons.Outlined.Apartment,"Mandi")
                            Text(text = mandiName, style = MaterialTheme.typography.bodyMedium)

                        }
                        Text(text = "Current Stock :", style = MaterialTheme.typography.bodySmall)
                    }

                    Text(
                        text = "${quantity.toInt()} kg",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}


@Composable
fun SlowMovingStockCard(
    productName: String = "Product 1",
    currentStock: Float = 80f,
    location: String = "Koyambedu Mandi",
    weeklySales: Float = 61.765f,
    daysInStock: Int = 34
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = productName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .border(2.dp, Color.Gray, shape = CircleShape)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        "$daysInStock days in stock",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = Color.Gray)
                        Spacer(modifier = Modifier.width(4.dp))
                        Column {
                            Text("Location", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                            Text(location, style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Stop, contentDescription = "Stock", tint = Color.Gray)
                        Spacer(modifier = Modifier.width(4.dp))
                        Column {
                            Text("Current Stock", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                            Text("$currentStock Kg", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))


                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Top
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.AutoMirrored.Filled.TrendingDown,
                            contentDescription = "Sales",
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Column {
                            Text("Weekly Sales", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                            Text("$weeklySales Kg", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

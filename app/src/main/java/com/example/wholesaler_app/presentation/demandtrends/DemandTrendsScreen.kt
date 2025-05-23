package com.example.wholesaler_app.presentation.demandtrends



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.security.KeyStore.Entry


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemandTrendsScreen(navController: NavController, screenWidth: Dp, screenHeight: Dp){
    Scaffold(
        topBar = {
            TopAppBar(
                title ={
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(0.dp,0.dp,screenWidth*0.15f,0.dp),
                            text = "Trends",
                            fontWeight = FontWeight.W900,

                            )
                    }
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew, // Use a "Back" icon here
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
    ) { innerPadding->
        val scrollState = rememberScrollState()
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

//                Text("Demand Trends Screen")

                TimeRangeCard()
                MultiSelectDropdownCard()
                SeasonalDemandTrendsCard()
                ProductDemandComparisonCard()

            }
        }

    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeRangeCard() {
    val timeRanges = listOf("3 months", "6 months", "12 months", "18 months", "24 months", "36 months")
    var selectedRange by remember { mutableStateOf(timeRanges.first()) }
    var expanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Time Range", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                TextField(
                    readOnly = true,
                    value = selectedRange,
                    onValueChange = {},
                    label = { Text("Select Time Range") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    timeRanges.forEach { range ->
                        DropdownMenuItem(text = { Text(range) }, onClick = {
                            selectedRange = range
                            expanded = false
                        })
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MultiSelectDropdownCard() {
    // Sample products
    val products = listOf("Product1", "Product2", "Product3", "Product4")

    // State to track selected items
    val selectedItems = remember { mutableStateListOf<String>() }
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors =  CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)


    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Select Products", style = MaterialTheme.typography.titleMedium)

            // Selected items shown here
            if (selectedItems.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    selectedItems.forEach { selected ->
                        AssistChip(
                            onClick = { /* Optional: handle click */ },
                            label = { Text(selected) }
                        )
                    }
                }
            } else {
                Text(
                    "No products selected",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Dropdown trigger
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(134,174,95)
                ),
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "Hide Options" else "Select Products")
            }

            // Dropdown options
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                products.forEach { product ->
                    val isSelected = selectedItems.contains(product)
                    DropdownMenuItem(
                        text = { Text(product) },
                        onClick = {
                            if (isSelected) selectedItems.remove(product)
                            else selectedItems.add(product)
                        },
                        trailingIcon = {
                            if (isSelected) Icon(Icons.Default.Check, contentDescription = null)
                        }
                    )
                }
            }
        }
    }
}



@Composable
fun SeasonalDemandTrendsCard() {
    val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug")
    val selectedProducts = listOf("Product1", "Product2", "Product3")

    // Dummy quantity data per product (each product has values for 8 months)
    val productData: Map<String, List<Float>> = mapOf(
        "Product1" to listOf(50000f, 80000f, 65000f, 70000f, 90000f, 110000f, 100000f, 95000f),
        "Product2" to listOf(30000f, 40000f, 50000f, 60000f, 75000f, 85000f, 95000f, 120000f),
        "Product3" to listOf(70000f, 85000f, 95000f, 105000f, 115000f, 125000f, 130000f, 140000f)
    )

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(300.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Seasonal Demand Trends",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            AndroidView(factory = { context ->
                LineChart(context).apply {
                    val lineDataSets = mutableListOf<ILineDataSet>()
                    val colors = listOf(Color.Red, Color.Blue, Color.Green)

                    selectedProducts.forEachIndexed { index, product ->
                        val entries = productData[product]?.mapIndexed { i, value ->
                            com.github.mikephil.charting.data.Entry(i.toFloat(), value)
                        } ?: emptyList()

                        val dataSet = LineDataSet(entries, product).apply {
                            color = colors[index % colors.size].toArgb()
                            valueTextColor = Color.Black.toArgb()
                            lineWidth = 2f
                            circleRadius = 3f
                            setCircleColor(color)
                            setDrawValues(false)
                        }

                        lineDataSets.add(dataSet)
                    }

                    val data = LineData(lineDataSets)
                    this.data = data

                    // Customize axes
                    xAxis.valueFormatter = IndexAxisValueFormatter(months)
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    xAxis.setDrawGridLines(false)
                    xAxis.granularity = 1f
                    xAxis.labelRotationAngle = -30f



                    axisLeft.axisMinimum = 0f
                    axisLeft.valueFormatter = object : ValueFormatter() {
                        override fun getFormattedValue(value: Float): String {
                            return "${value.toInt() / 1000}k"
                        }
                    }

                    axisRight.isEnabled = false

                    description.isEnabled = false
                    legend.isEnabled = true

                    invalidate()
                }
            }, modifier = Modifier.fillMaxSize())
        }
    }
}



@Composable
fun ProductDemandComparisonCard() {
    val products = listOf("Product1", "Product2", "Product3")
    val months = listOf("Jan", "Feb", "Mar")

    val dummyData: Map<String, List<Float>> = mapOf(
        "Jan" to listOf(60000f, 90000f, 70000f),
        "Feb" to listOf(85000f, 95000f, 65000f),
        "Mar" to listOf(75000f, 80000f, 90000f)
    )

    val monthColors = listOf(Color(0xFF4CAF50), Color(0xFF2196F3), Color(0xFFFF9800)) // Green, Blue, Orange

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(350.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Product Demand Comparison", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(16.dp))

            AndroidView(factory = { context ->
                BarChart(context).apply {
                    val groupCount = products.size
                    val barWidth = 0.2f
                    val barSpace = 0.05f
                    val groupSpace = 0.1f

                    val monthDataSets = months.mapIndexed { monthIndex, month ->
                        val productQuantities = dummyData[month] ?: List(groupCount) { 0f }
                        val entries = productQuantities.mapIndexed { productIndex, quantity ->

                            BarEntry(productIndex.toFloat(), quantity)
                        }

                        BarDataSet(entries, month).apply {
                            color = monthColors[monthIndex % monthColors.size].toArgb()
                            setDrawValues(false)
                        }
                    }

                    val barData = BarData(monthDataSets)
                    barData.barWidth = barWidth

                    this.data = barData
                    this.setFitBars(true)

                    xAxis.apply {
                        valueFormatter = IndexAxisValueFormatter(products)
                        position = XAxis.XAxisPosition.BOTTOM
                        granularity = 1f
                        setCenterAxisLabels(true)
                        setDrawGridLines(false)
                        axisMinimum = 0f
                        axisMaximum = 0f + groupCount
                        labelRotationAngle = -15f
                    }

                    axisLeft.apply {
                        axisMinimum = 0f
                        granularity = 1f
                        valueFormatter = object : ValueFormatter() {
                            override fun getFormattedValue(value: Float): String {
                                return "${value.toInt() / 1000}k"
                            }
                        }
                    }

                    axisRight.isEnabled = false

                    legend.isEnabled = true
                    description.isEnabled = false

                    barData.groupBars(0f, groupSpace, barSpace)
                    invalidate()
                }
            }, modifier = Modifier.fillMaxSize())
        }
    }
}


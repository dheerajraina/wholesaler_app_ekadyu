package com.example.wholesaler_app.presentation.marketcomparison


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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketComparisonScreen(navController: NavController, screenWidth: Dp, screenHeight: Dp){
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

//                Text("Market Comparison Screen")
                MultiMandiSelectDropdownCard()
                MultiProductSelectDropdownCard()
                MarketComparisonCard()

            }



        }

    }
}



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MultiMandiSelectDropdownCard() {
    val mandis = listOf("Azadpur Mandi", "Koyambedu Mandi", "Vashi Mandi", "Ghazipur Mandi","Okhla Mandi")

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
            Text("Select Mandis", style = MaterialTheme.typography.titleMedium)

            if (selectedItems.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    selectedItems.forEach { selected ->
                        AssistChip(
                            onClick = { },
                            label = { Text(selected) }
                        )
                    }
                }
            } else {
                Text(
                    "No Mandis selected",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(134,174,95)
                ),
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "Hide Options" else "Select Mandis")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                mandis.forEach { mandi ->
                    val isSelected = selectedItems.contains(mandi)
                    DropdownMenuItem(
                        text = { Text(mandi) },
                        onClick = {
                            if (isSelected) selectedItems.remove(mandi)
                            else selectedItems.add(mandi)
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




@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MultiProductSelectDropdownCard() {

    val products = listOf("Potato", "Onion", "Tomato", "Cauliflower","Green Peas","Cabbage")

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

            if (selectedItems.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    selectedItems.forEach { selected ->
                        AssistChip(
                            onClick = { },
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

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(134,174,95)
                ),
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "Hide Options" else "Select Products")
            }

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
fun MarketComparisonCard() {
    val products = listOf("Tomato", "Onion","Peas","Potato")
    val mandis = listOf("Delhi Mandi", "Mumbai Mandi", "Lucknow Mandi")

    val dummyData: Map<String, List<Float>> = mapOf(
        "Delhi Mandi" to listOf(22f, 14f,100f,30f ),
        "Mumbai Mandi" to listOf(25f, 18f,80f,20f ),
        "Lucknow Mandi" to listOf(20f, 14f,75f,25f )
    )

    val mandiColors= listOf(Color(0xFF4CAF50), Color(0xFF2196F3), Color(0xFFFF9800)) // Green, Blue, Orange

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
            Text("Price Comparison Across Markets", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(16.dp))


                AndroidView(factory = { context ->
                    BarChart(context).apply {
                        val groupCount = products.size
                        val barWidth = 0.2f
                        val barSpace = 0.05f
                        val groupSpace = 0.1f

                        val mandiDataSets = mandis.mapIndexed { mandiIndex, mandi ->
                            val productQuantities = dummyData[mandi] ?: List(groupCount) { 0f }
                            val entries = productQuantities.mapIndexed { productIndex, quantity ->
                                BarEntry(productIndex.toFloat(), quantity)
                            }

                            BarDataSet(entries, mandi).apply {
                                color = mandiColors[mandiIndex % mandiColors.size].toArgb()
                                setDrawValues(false)
                            }
                        }

                        val barData = BarData(mandiDataSets)
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


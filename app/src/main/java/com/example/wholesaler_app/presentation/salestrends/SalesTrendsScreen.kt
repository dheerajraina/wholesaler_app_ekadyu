package com.example.wholesaler_app.presentation.salestrends

import android.content.Context
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesTrendsScreen(navController: NavController, screenWidth: Dp, screenHeight: Dp){
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
            SalesTabBar()
        }

    }
}



@Composable
fun SalesTabBar() {
    val tabs = listOf("Sales Trends", "Top Products")
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
            0 -> SalesTrendsContent()
            1 -> TopProductsContent()
        }
    }
}

@Composable
fun SalesTrendsContent() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
//            .background(Color(0xFFD0F0C0)), // light green
        contentAlignment = Alignment.Center
    ) {
//        Text("Sales Trends Page", style = MaterialTheme.typography.headlineMedium)
         Column {
             PeriodDropdownMenu()
             Spacer(Modifier.height(30.dp))
             MonthlySalesTrendsCard()
         }
    }

}




@Composable
fun TopProductsContent() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
//            .background(Color(0xFFFFE4B5)),
        contentAlignment = Alignment.Center
    ) {
        Column {
            PeriodDropdownMenu()
            Spacer(Modifier.height(15.dp))
            ViewDropdownMenu()
            Spacer(Modifier.height(30.dp))
            TopProductsChartCard()

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeriodDropdownMenu(
    onPeriodSelected: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Select Period") }

    val options = listOf("Weekly", "Monthly", "Quarterly", "Yearly")

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
fun MonthlySalesTrendsCard(context: Context = LocalContext.current) {
    val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun")
    val revenueData = listOf(120000f, 150000f, 100000f, 180000f, 160000f, 200000f)
    val orderData = listOf(130f, 105f, 110f, 60f, 95f, 80f)

    var expanded by remember { mutableStateOf(false) }
    var chartRef by remember { mutableStateOf<LineChart?>(null) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {


            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Monthly Sales Trends", style = MaterialTheme.typography.titleMedium)

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
                                    val filename = "sales_chart"
                                    it.saveToGallery(
                                        filename,
                                        100
                                    ) // Quality = 100
                                    Toast.makeText(context, "Saved PNG to gallery", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Download CSV") },
                            onClick = {
                                expanded = false

                            }
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
                    LineChart(ctx).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            500

                        )
                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        



                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            granularity = 1f
                            valueFormatter = IndexAxisValueFormatter(months)
                            textColor = Color.Black.toArgb()
                        }

                        axisLeft.apply {
                            axisMinimum = 0f
                            valueFormatter = object : ValueFormatter() {
                                override fun getFormattedValue(value: Float): String {
                                    return "${(value / 1000).toInt()}k"
                                }
                            }
                            textColor = Color(0xFF4CAF50).toArgb()
                        }

                        axisRight.apply {
                            axisMinimum = 0f
                            textColor = Color(0xFF2196F3).toArgb()
                        }

                        val revenueEntries = revenueData.mapIndexed { index, value ->
                            Entry(index.toFloat(), value)
                        }

                        val orderEntries = orderData.mapIndexed { index, value ->
                            Entry(index.toFloat(), value)
                        }

                        val revenueSet = LineDataSet(revenueEntries, "Revenue (Rs.)").apply {
                            color = Color(0xFF4CAF50).toArgb()
                            axisDependency = YAxis.AxisDependency.LEFT
                            setDrawValues(false)
                        }

                        val ordersSet = LineDataSet(orderEntries, "Total Orders").apply {
                            color = Color(0xFF2196F3).toArgb()
                            axisDependency = YAxis.AxisDependency.RIGHT
                            setDrawValues(false)
                        }

                        data = LineData(revenueSet, ordersSet)
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






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewDropdownMenu(
    onViewSelected: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("View By") }

    val options = listOf("By Volume(kg)", "By Revenue(Rs.)")

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
                        onViewSelected(selectionOption)
                    }
                )
            }
        }
    }
}



@Composable
fun TopProductsChartCard(context: Context = LocalContext.current) {
    val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun")
    val volumeData = listOf(1.25f, 2.0f, 0.75f, 3.5f, 2.8f, 4.1f)

    // Find max Y value from data
    val maxVolume = (volumeData.maxOrNull() ?: 0f) + 1f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Top Products by Volume - Monthly",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            AndroidView(
                factory = { ctx ->
                    LineChart(ctx).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            500
                        )

                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        setPinchZoom(true)
                        setDrawGridBackground(false)

                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            granularity = 1f
                            valueFormatter = IndexAxisValueFormatter(months)
                            textColor = Color.Black.toArgb()
                            setDrawGridLines(false)
                        }


                        axisLeft.apply {
                            axisMinimum = 0f
                            axisMaximum = maxVolume
                            textColor = Color(0xFF4CAF50).toArgb()
                            valueFormatter = object : ValueFormatter() {
                                override fun getFormattedValue(value: Float): String {
                                    return "%.2f kg".format(value)
                                }
                            }
                            setDrawGridLines(false)
                        }

                        axisRight.isEnabled = false


                        val volumeEntries = volumeData.mapIndexed { index, value ->
                            Entry(index.toFloat(), value)
                        }

                        val volumeSet = LineDataSet(volumeEntries, "Sales Volume").apply {
                            color = Color(0xFF4CAF50).toArgb()
                            setDrawCircles(false)
                            setDrawValues(false)
                            lineWidth = 2f
                            setDrawHighlightIndicators(false)
                        }

                        data = LineData(volumeSet)
                        invalidate()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }
    }
}




package com.example.wholesaler_app.presentation.trends

import android.service.autofill.OnClickAction
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendsScreen(navController: NavController, screenWidth: Dp, screenHeight: Dp){
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

                CustomCard(Icons.Default.AutoGraph,"Sales Trends",screenHeight,screenWidth,{navController.navigate("sales_trends")})
                CustomCard(Icons.AutoMirrored.Filled.TrendingUp,"Demand Trends",screenHeight,screenWidth,{navController.navigate("demand_trends")})
                CustomCard(Icons.Outlined.Storefront,"Stock Insights",screenHeight,screenWidth,{navController.navigate("stock_insights")})
                CustomCard(Icons.Outlined.BarChart,"Market Comparison",screenHeight,screenWidth,{navController.navigate("market_comparison")})

            }
        }

    }
}



@Composable
fun CustomCard(icon: ImageVector, text:String,screenHeight: Dp,screenWidth: Dp,onClick:()->Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = screenWidth*0.08f, vertical = screenHeight*0.01f)
            .size(width = 0.dp, height=screenHeight*0.12f )
            .clickable{onClick()},

        elevation =   CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row (
            modifier = Modifier
                .padding(horizontal = screenWidth*0.02f, vertical = screenHeight*0.04f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint =  Color(134,174,95),
                modifier = Modifier
                    .padding(end = 16.dp)
            )
            Text(
                text,
                fontWeight = FontWeight.Bold,
                style = TextStyle(fontSize = 20.sp)
                )
        }
    }
}

package com.example.wholesaler_app.presentation.home


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wholesaler_app.presentation.createorder.CreateOrderScreen
import com.example.wholesaler_app.presentation.demandtrends.DemandTrendsScreen
import com.example.wholesaler_app.presentation.home.components.CardListWithShowMore
import com.example.wholesaler_app.presentation.home.components.HomeBottomBar
import com.example.wholesaler_app.presentation.home.components.HomeSearchBar
import com.example.wholesaler_app.presentation.home.components.HomeTopBar
import com.example.wholesaler_app.presentation.marketcomparison.MarketComparisonScreen
import com.example.wholesaler_app.presentation.pastorders.PastOrdersScreen
import com.example.wholesaler_app.presentation.trends.TrendsScreen
import com.example.wholesaler_app.presentation.myorders.MyOrdersScreen
import com.example.wholesaler_app.presentation.salestrends.SalesTrendsScreen
import com.example.wholesaler_app.presentation.stockinsights.StockInsightsScreen


@Composable
fun HomeScreen() {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp


    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home_main"
    ){
        composable("home_main"){
            Scaffold(
                containerColor = Color.White,
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    HomeTopBar(navController,5,5,screenWidth,screenHeight)
                },
                bottomBar = {
                    HomeBottomBar(navController,screenWidth,screenHeight)
                }

            ) { innerPadding ->
                // Content area (you can add other content here later)
                Box(modifier = Modifier.padding(innerPadding)) {
                    // Add other screen content here if necessary
                    HomeSearchBar(screenWidth,screenHeight)
                    CardListWithShowMore(screenWidth,screenHeight)
                }
            }


        }
        composable("my_orders"){
            MyOrdersScreen(navController,screenWidth,screenHeight)
        }
        composable("create_order"){
            CreateOrderScreen(navController,screenWidth,screenHeight)
        }
        composable("past_orders"){
//            PastOrdersScreen()
            PastOrdersScreen(navController,screenWidth,screenHeight)
        }
        composable("trends"){
            TrendsScreen(navController,screenWidth,screenHeight)
        }

        composable("sales_trends"){
            SalesTrendsScreen(navController,screenWidth,screenHeight)
        }
        composable("demand_trends"){
            DemandTrendsScreen(navController,screenWidth,screenHeight)
        }

        composable("stock_insights"){
            StockInsightsScreen(navController,screenWidth,screenHeight)
        }
        composable("market_comparison"){
            MarketComparisonScreen(navController,screenWidth,screenHeight)
        }


    }
}

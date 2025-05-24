package com.example.wholesaler_app.data.remote

import com.example.wholesaler_app.data.dtos.OrderItemDetails
import retrofit2.http.GET

interface ApiService {
    @GET("/getOrderItemDetails")
    suspend fun getOrderItemDetails(): List<OrderItemDetails>


    @GET("/getCompletedOrderSummary")
    suspend fun getCompletedOrders(): List<OrderItemDetails>


}

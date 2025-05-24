package com.example.wholesaler_app.data.dtos

import com.google.gson.annotations.SerializedName





data class OrderDto(
    @SerializedName("orderId") val orderId: Int,
    @SerializedName("orderItems") val items: List<OrderItemDto>,
    @SerializedName("totalOrderAmount") val total: Int
)

data class OrderItemDto(
    @SerializedName("order_item_id") val orderItemId: Int,
    @SerializedName("product_id") val productId: Int,
    @SerializedName("product_name") val productName: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("unit_id") val unitId: Int,
    @SerializedName("unit_name") val unitName: String,
    @SerializedName("max_item_price") val maxItemPrice: Int
)

data class OrderItemDetailsDto(
    @SerializedName("order_id") val orderId: Int,
    @SerializedName("retailer_id") val retailerId: Int,
    @SerializedName("retailer_name") val retailerName: String,
    @SerializedName("retailer_address") val retailerAddress: String,
    @SerializedName("retailer_mobile") val retailerMobile: String,
    @SerializedName("actual_delivery_date") val actualDeliveryDate: String,
    @SerializedName("order_status_id") val orderStatusId: Int,
    @SerializedName("order_status") val orderStatus: String,
    @SerializedName("total_order_amount") val totalOrderAmount: Double,
    @SerializedName("order_items") val orderItems: List<OrderItem>,
    @SerializedName("created_at") val createdAt: String
)

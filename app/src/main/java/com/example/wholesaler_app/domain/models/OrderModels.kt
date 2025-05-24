package com.example.wholesaler_app.data.dtos


data class Order(
    val id: Int,
    val items: List<String>,
    val total: Int
)



data class OrderItem(
    val order_item_id: Int,
    val product_id: Int,
    val product_name: String,
    val quantity: Int,
    val unit_id: Int,
    val unit_name: String,
    val max_item_price: Int
)

data class OrderItemDetails(
    val order_id: Int,
    val total_order_amount: Int,
    val order_items: List<OrderItem>,
    val created_at: String? = null
)
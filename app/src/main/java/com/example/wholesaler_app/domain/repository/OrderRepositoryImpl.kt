package com.example.wholesaler_app.domain.repository

import com.example.wholesaler_app.data.dtos.Order
import com.example.wholesaler_app.data.dtos.OrderDto
import com.example.wholesaler_app.data.dtos.OrderItem
import com.example.wholesaler_app.data.dtos.OrderItemDetails
import com.example.wholesaler_app.data.repository.OrderRepositoryImpl


class OrderRepositoryImpl {
    private val dataRepository = OrderRepositoryImpl()
    suspend fun getOrders(): List<Order> {

        val orders : List<OrderDto> = dataRepository.getOrders()
        return orders.map {order  ->
            Order(
                id = order.orderId,
                items = order.items.mapIndexed { index, item ->
                    "Item ${index + 1}: ${item.productName} - ${item.quantity} ${item.unitName} (Rs. ${item.maxItemPrice}/${item.unitName})"
                },
                total = order.total
            )
        }
    }

    suspend fun getCompletedOrders(): List<OrderItemDetails> {

        val completedOrders:List<OrderDto> = dataRepository.getCompletedOrders()

        return completedOrders.map { completedOrder ->
            OrderItemDetails(
                order_id = completedOrder.orderId,
                total_order_amount = (completedOrder.total).toInt(),

                order_items = completedOrder.items.mapIndexed { index, item ->
                    OrderItem(
                        order_item_id = item.orderItemId,
                        product_id = item.productId,
                        product_name = item.productName,
                        quantity = item.quantity,
                        unit_id = item.unitId,
                        unit_name = item.unitName,
                        max_item_price = (item.maxItemPrice).toInt()
                    )
                })

        }
    }
}


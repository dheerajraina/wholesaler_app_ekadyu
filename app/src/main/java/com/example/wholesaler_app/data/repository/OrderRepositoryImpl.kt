package com.example.wholesaler_app.data.repository

import com.example.wholesaler_app.data.dtos.OrderDto
import com.example.wholesaler_app.data.dtos.OrderItemDto
import com.example.wholesaler_app.data.remote.RetrofitInstance


class OrderRepositoryImpl {
    suspend fun getOrders(): List<OrderDto> {
        return RetrofitInstance.api.getOrderItemDetails().map { orderDetail ->
            OrderDto(
                orderId = orderDetail.order_id,
                items = orderDetail.order_items.mapIndexed { index, item ->
//                    "Item ${index + 1}: ${item.productName} - ${item.quantity} ${item.unitName} (Rs. ${item.maxItemPrice}/${item.unitName})"
                    OrderItemDto(
                        orderItemId = item.order_item_id,
                        productName = item.product_name,
                        productId = item.product_id,
                        unitId = item.unit_id,
                        quantity = item.quantity,
                        unitName = item.unit_name,
                        maxItemPrice = item.max_item_price
                    )
                                                           },
                total = orderDetail.total_order_amount
            )
        }
    }

    suspend fun getCompletedOrders(): List<OrderDto> {
        return RetrofitInstance.api.getCompletedOrders().map { completedOrder ->
            OrderDto(
                orderId = completedOrder.order_id,
                total = completedOrder.total_order_amount,

                items = completedOrder.order_items.mapIndexed { index, item ->
                    OrderItemDto(
                        orderItemId = item.order_item_id,
                        productId = item.product_id,
                        productName = item.product_name,
                        quantity = item.quantity,
                        unitId = item.unit_id,
                        unitName = item.unit_name,
                        maxItemPrice = item.max_item_price
                    )
                })

        }
    }
}

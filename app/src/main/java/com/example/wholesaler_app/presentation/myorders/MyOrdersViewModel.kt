package com.example.wholesaler_app.presentation.myorders

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wholesaler_app.data.dtos.Order
import com.example.wholesaler_app.domain.repository.OrderRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyOrdersViewModel(
    private val repository: OrderRepositoryImpl
) : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    init {
        fetchOrders()
    }

    private fun fetchOrders() {
        viewModelScope.launch {
            try {
                val result =repository.getOrders()
                Log.d("MyOrdersViewModel", "Fetched ${result.size} orders")
                _orders.value = result
            } catch (e: Exception) {
                Log.e("MyOrdersViewModel", "Error fetching orders", e)
                e.printStackTrace()
            }
        }
    }
}

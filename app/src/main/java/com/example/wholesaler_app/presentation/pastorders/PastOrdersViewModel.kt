package com.example.wholesaler_app.presentation.pastorders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wholesaler_app.data.dtos.OrderItemDetails
import com.example.wholesaler_app.domain.repository.OrderRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PastOrdersViewModel(private val repository: OrderRepositoryImpl) : ViewModel() {

    private val _completedOrders = MutableStateFlow<List<OrderItemDetails>>(emptyList())
    val completedOrders: StateFlow<List<OrderItemDetails>> = _completedOrders

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _hasError = MutableStateFlow(false)
    val hasError: StateFlow<Boolean> = _hasError


    init {
        loadCompletedOrders()
    }

    private fun loadCompletedOrders() {
        _isLoading.value = true
        _hasError.value = false
        viewModelScope.launch {
            try {
                val result = repository.getCompletedOrders()
                _completedOrders.value = result
            } catch (e: Exception) {
                _hasError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}



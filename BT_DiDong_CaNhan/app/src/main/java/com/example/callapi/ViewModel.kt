package com.example.callapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callapi.entity.Product
import com.example.callapi.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val repository = ProductRepository()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            when (val result = repository.getProducts()) {
                is Result.Success -> {
                    _products.value = result.data
                }
                is Result.Failure -> {
                    _errorMessage.value = result.exception.message
                }
            }

            _isLoading.value = false
        }
    }
}
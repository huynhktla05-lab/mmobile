package com.example.callapi.repository
import com.example.callapi.entity.Product
import com.example.callapi.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository {
    private val apiService = RetrofitClient.productApiService

    suspend fun getProducts(): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getProducts()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("API Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
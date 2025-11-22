package com.example.callapi.service

import com.example.callapi.entity.Product
import retrofit2.Response
import retrofit2.http.GET

interface ProductApiService {
    @GET("v2/product")
    suspend fun getProducts(): Response<List<Product>>

    // Hoặc nếu API trả về object chứa list
    // @GET("v2/product")
    // suspend fun getProducts(): Response<ProductResponse>
}
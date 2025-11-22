package com.example.callapi.entity


data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String?,
    val image: String?
)

data class ProductResponse(
    val products: List<Product>
)
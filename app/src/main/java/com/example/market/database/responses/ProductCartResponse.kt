package com.example.market.database.responses

data class ProductCartResponse(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val currentPrice: Int,
    val amount: Int,
    val currentAmount: Int
)

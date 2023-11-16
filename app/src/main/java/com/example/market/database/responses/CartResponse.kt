package com.example.market.database.responses

data class CartResponse(
    val id: Int,
    val products: List<ProductCartResponse>
)

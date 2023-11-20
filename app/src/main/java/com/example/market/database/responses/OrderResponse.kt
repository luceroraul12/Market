package com.example.market.database.responses

data class OrderResponse(
    val id: Int?,
    val username: String,
    val storeCode: String,
    val products: List<ProductOrderResponse>
)

data class ProductOrderResponse(
    val productId: Int,
    val unitName: String,
    val unitValue: Double,
    val unitPrice: Int,
    val amount: Int,
)

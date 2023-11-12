package com.example.market.ui.products.model

import com.example.market.database.responses.ProductCustomerResponse


data class ProductViewModel(
    val product: ProductCustomerResponse,
    var currentAmount: Int = 0,
    var currentPrice: Double = 0.0,
    var isCart: Boolean = false
)

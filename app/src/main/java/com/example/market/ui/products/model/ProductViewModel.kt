package com.example.market.ui.products.model

import com.example.market.database.entities.ProductEntity


data class ProductViewModel(
    val product: ProductEntity,
    var currentAmount: Int = 0,
    var currentPrice: Double = 0.0,
    var isCart: Boolean = false
)

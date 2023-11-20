package com.example.market.ui.products.model

import com.example.market.database.entities.ProductEntity
import com.example.market.database.responses.ProductOrderResponse


data class ProductViewModel(
    val product: ProductEntity,
    var currentAmount: Int = 0,
    var currentPrice: Double = 0.0,
    var isCart: Boolean = false
)

fun ProductViewModel.toProductEntity(): ProductEntity{
    return this.product
}

fun ProductViewModel.toProductOrderResponse(): ProductOrderResponse{
    return ProductOrderResponse(
        productId = product.id,
        unitName = product.unitTypeName,
        unitPrice = product.price,
        unitValue = product.unitTypeValue,
        amount = currentAmount,
    )
}
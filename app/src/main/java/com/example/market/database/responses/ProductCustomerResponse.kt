package com.example.market.database.responses

import com.example.market.database.entities.ProductEntity
import com.example.market.ui.products.model.ProductViewModel

data class ProductCustomerResponse(
    val id: Int,
    val stock: Boolean,
    val onlyUnit: Boolean,
    val name: String,
    val description: String?,
    val price: Int,
    val category: LookupValueResponse,
    val unitType: LookupValueResponse
)

public fun ProductCustomerResponse.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        stock = stock,
        onlyUnit = onlyUnit,
        name = name,
        description = description,
        price = price,
        categoryName = category.description,
        categoryCode = category.code,
        unitTypeName = unitType.description,
        unitTypeValue = unitType.value.toDouble()
    )
}
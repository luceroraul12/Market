package com.example.market.database.responses

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
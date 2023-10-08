package com.example.market.data

import com.example.market.ui.products.model.ProductViewModel

class DataMock {
    val products: List<ProductViewModel> = listOf(
        ProductViewModel("mani", "pelado", "x100g", 1500),
        ProductViewModel("mani", "con cascara", "x100g", 1300),
        ProductViewModel("almendra", "", "x100g", 3000),
        ProductViewModel("mix frutos secos", "mani, almendra, nueces, pasas de uva", "x100g", 1850),
    )
}
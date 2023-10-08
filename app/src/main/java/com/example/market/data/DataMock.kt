package com.example.market.data

import com.example.market.ui.products.model.ProductViewModel
import java.io.Serializable

class DataMock {
    val products: List<ProductViewModel> = listOf(
        ProductViewModel(1,"mani", "pelado", "x100g", 1500),
        ProductViewModel(2,"mani", "con cascara", "x100g", 1300),
        ProductViewModel(3,"almendra", "", "x100g", 3000),
        ProductViewModel(4,"mix frutos secos", "mani, almendra, nueces, pasas de uva", "x100g", 1850),
    )


    fun getById(id: Int): ProductViewModel {
        return products.first { p -> p.id == id }
    }
}
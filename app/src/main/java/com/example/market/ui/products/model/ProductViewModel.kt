package com.example.market.ui.products.model

import android.os.Parcelable
import java.io.Serializable

data class ProductViewModel(
    val id: Int = 0,
    val name: String = "VACIO",
    val description: String = "VACIO",
    val unit: String = "VACIO",
    val price: Int = 0,
    var currentAmount: Int = 0,
    var currentPrice: Int = 0
)

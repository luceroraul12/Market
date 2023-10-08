package com.example.market.ui.products.model

import android.os.Parcelable
import java.io.Serializable

data class ProductViewModel(
    val id: Int,
    val name: String,
    val description: String,
    val unit: String,
    val price: Int
)

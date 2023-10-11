package com.example.market.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.market.ui.products.model.ProductViewModel

@Entity
data class ProductCartEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "price") val price: Int
)
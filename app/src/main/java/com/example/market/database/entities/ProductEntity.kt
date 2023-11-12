package com.example.market.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.market.ui.products.model.ProductViewModel

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "stock") val stock: Boolean,
    @ColumnInfo(name = "onlyUnit") val onlyUnit: Boolean,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "categoryName") val categoryName: String,
    @ColumnInfo(name = "categoryCode") val categoryCode: String,
    @ColumnInfo(name = "unitTypeName") val unitTypeName: String,
    @ColumnInfo(name = "unitTypeValue") val unitTypeValue: Double,
)
fun ProductEntity.toViewModel(): ProductViewModel{
    return ProductViewModel(this)
}
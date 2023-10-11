package com.example.market.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.market.database.entities.ProductCartEntity

@Dao
interface ProductCartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productCart: ProductCartEntity)

    @Query("DELETE FROM productcartentity WHERE id = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM ProductCartEntity")
    fun getAll(): List<ProductCartEntity>
}
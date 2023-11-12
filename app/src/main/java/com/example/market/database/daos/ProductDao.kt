package com.example.market.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.market.database.entities.ProductEntity

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: ProductEntity)

    @Query("DELETE FROM productcartentity WHERE id = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM ProductCartEntity")
    fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM ProductCartEntity WHERE id = :id")
    fun getById(id: Int): ProductEntity
}
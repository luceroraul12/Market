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

    @Query("DELETE FROM ProductEntity WHERE id = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM ProductEntity")
    fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE id = :id")
    fun getById(id: Int): ProductEntity
}
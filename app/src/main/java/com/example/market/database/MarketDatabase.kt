package com.example.market.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.market.database.daos.ProductCartDao
import com.example.market.database.entities.ProductCartEntity

@Database(entities = [ProductCartEntity::class], version = 1)
abstract class MarketDatabase: RoomDatabase() {
    abstract fun getProductCartDao(): ProductCartDao
}
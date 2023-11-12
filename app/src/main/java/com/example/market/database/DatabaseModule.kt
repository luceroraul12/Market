package com.example.market.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(context, MarketDatabase::class.java, "product_cart_database").build()

    @Singleton
    @Provides
    fun provideProductCartDao(db: MarketDatabase) = db.getProductCartDao()

    @Singleton
    @Provides
    fun provideProductDao(db: MarketDatabase) = db.getProductDao()
}
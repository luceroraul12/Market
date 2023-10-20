package com.example.market.database.repository

import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.market.database.daos.ProductCartDao
import com.example.market.database.entities.ProductCartEntity
import javax.inject.Inject

class ProductCartRepositoryImpl @Inject constructor(
    private val productCartDao: ProductCartDao
) {
    suspend fun insert(productCartEntity: ProductCartEntity){
        
    }

    fun star(){
        Log.i("raul", "inyectadooooooo")
    }
}
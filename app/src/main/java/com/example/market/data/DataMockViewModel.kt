package com.example.market.data

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.market.database.daos.ProductCartDao
import com.example.market.database.entities.ProductCartEntity
import com.example.market.ui.products.model.ProductViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DataMockViewModel @Inject constructor(
    private val productCartDao: ProductCartDao
): ViewModel(){
    val products: List<ProductViewModel> = listOf(
        ProductViewModel(1,"mani", "pelado", "x100g", 1500),
        ProductViewModel(2,"mani","con cascara", "x100g", 1300),
        ProductViewModel(3,"almendra", "", "x100g", 3000),
        ProductViewModel(4,"mix frutos secos", "mani, almendra, nueces, pasas de uva", "x100g", 1850),
    )


    fun getById(id: Int): ProductViewModel {
        return products.first { p -> p.id == id }
    }

    fun insert(productCartEntity: ProductCartEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            productCartDao.insert(productCartEntity);
        }
    }

    fun getProductsCart(): List<ProductCartEntity> {
        return productCartDao.getAll();
    }

    fun removeProductCart(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            productCartDao.delete(id)
        }
    }
}
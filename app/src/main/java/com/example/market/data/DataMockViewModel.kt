package com.example.market.data

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.market.database.daos.ProductCartDao
import com.example.market.database.entities.ProductCartEntity
import com.example.market.database.repository.NetworkRepository
import com.example.market.database.responses.toViewModel
import com.example.market.ui.products.model.ProductViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataMockViewModel @Inject constructor(
    private val productCartDao: ProductCartDao,
    private val networkRepository: NetworkRepository
): ViewModel(){
    private var products: List<ProductViewModel> = emptyList()
//        listOf(
//        ProductViewModel(1,"mani", "pelado", "100", 1500, 0, 0.0, true),
//        ProductViewModel(2,"mani","con cascara", "100", 1300),
//        ProductViewModel(3,"almendra", "", "100", 3000),
//        ProductViewModel(4,"mix frutos secos", "mani, almendra, nueces, pasas de uva", "100", 1850),
//    )

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

    suspend fun getAllProductaWithStatus(): List<ProductViewModel> {
        products = networkRepository.example().map { it.toViewModel() }
        val productCartIds = getProductsCart().map { it.id }
        val result: List<ProductViewModel> = products.map {
            it.isCart = productCartIds.contains(it.id)
            it
        }
        return result
    }

    fun removeProductCart(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            productCartDao.delete(id)
        }
    }

    fun cleanProductCart(){
        CoroutineScope(Dispatchers.IO).launch {
            productCartDao.cleanProductCart()
        }
    }
}
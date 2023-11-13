package com.example.market.data

import androidx.lifecycle.ViewModel
import com.example.market.database.daos.ProductCartDao
import com.example.market.database.daos.ProductDao
import com.example.market.database.entities.ProductCartEntity
import com.example.market.database.entities.toViewModel
import com.example.market.database.repository.NetworkRepository
import com.example.market.database.responses.ProductCustomerResponse
import com.example.market.database.responses.toEntity
import com.example.market.ui.products.model.ProductViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataProductViewModel @Inject constructor(
    private val productCartDao: ProductCartDao,
    private val productDao: ProductDao,
    private val networkRepository: NetworkRepository
): ViewModel(){
    private var products: List<ProductViewModel> = emptyList()
    suspend fun getById(id: Int): ProductViewModel {
        return productDao.getById(id).toViewModel()
    }

    fun insert(productCartEntity: ProductCartEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            productCartDao.insert(productCartEntity);
        }
    }

    suspend fun getProductsCart(): List<ProductCartEntity> {
        val result: List<ProductCustomerResponse> = networkRepository.getProducts()
        productDao.insert(result.map { it.toEntity() })
        return productCartDao.getAll();
    }

    suspend fun getAllProductaWithStatus(): List<ProductViewModel> {
        val productCartIds: List<Int> = getProductsCart().map { it.id }
        val result: List<ProductViewModel> = productDao.getAll().map {
            ProductViewModel(
                product = it,
                isCart = productCartIds.contains(it.id)
            )
        }
        this.products = result
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

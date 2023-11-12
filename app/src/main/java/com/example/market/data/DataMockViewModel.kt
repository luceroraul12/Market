package com.example.market.data

import androidx.lifecycle.ViewModel
import com.example.market.database.daos.ProductCartDao
import com.example.market.database.entities.ProductCartEntity
import com.example.market.database.repository.NetworkRepository
import com.example.market.database.responses.ProductCustomerResponse
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
    fun getById(id: Int): ProductViewModel {
        return products.first { it.product.id == id }
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
        val productsResponse: List<ProductCustomerResponse> = networkRepository.getProducts()
        val productCartIds: List<Int> = getProductsCart().map { it.id }
        val result: List<ProductViewModel> = productsResponse.map {
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
package com.example.market.ui.orders

import androidx.lifecycle.ViewModel
import com.example.market.database.repository.NetworkRepository
import com.example.market.database.responses.OrderResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel  @Inject constructor(
    private val networkRepository: NetworkRepository
): ViewModel(){
    suspend fun getMyOrders(storeCode: String, username: String): List<OrderResponse> {
        return networkRepository.getMyOrders(username, storeCode)
    }
}
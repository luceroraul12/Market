package com.example.market.database.repository

import com.example.market.database.responses.OrderResponse
import com.example.market.database.responses.ProductCustomerResponse
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val service: NetworkApiService
) {
    suspend fun getProducts(): List<ProductCustomerResponse> {
        return service.getProducts()
    }

    suspend fun createOrder(order: OrderResponse): Boolean {
        return service.createOrder(order)
    }

    suspend fun getOrders(username: String, storeCode: String): List<OrderResponse> {
        return service.getOrders(username, storeCode)
    }
}
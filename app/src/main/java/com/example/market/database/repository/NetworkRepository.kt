package com.example.market.database.repository

import com.example.market.database.responses.ProductCustomerResponse
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val service: NetworkApiService
) {

    suspend fun getProducts(): List<ProductCustomerResponse> {
        return service.getProducts()
    }
}
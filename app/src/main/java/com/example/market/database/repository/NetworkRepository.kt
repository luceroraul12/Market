package com.example.market.database.repository

import com.example.market.database.responses.ProductoInternoResponse
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val service: NetworkApiService
) {

    suspend fun example(): List<ProductoInternoResponse> {
        return service.getProducts()
    }
}
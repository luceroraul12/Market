package com.example.market.database.repository

import com.example.market.database.responses.ProductCustomerResponse
import retrofit2.http.GET

interface NetworkApiService {

    @GET("/customer/products")
    suspend fun getProducts(): List<ProductCustomerResponse>
}
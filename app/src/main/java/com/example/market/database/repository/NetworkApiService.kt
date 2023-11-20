package com.example.market.database.repository

import com.example.market.database.responses.OrderResponse
import com.example.market.database.responses.ProductCustomerResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NetworkApiService {

    @GET("/customer/products")
    suspend fun getProducts(): List<ProductCustomerResponse>

    @POST("/customer/cart")
    suspend fun createOrder(@Body order: OrderResponse): Boolean

    @GET("/customer/cart/store/{storeCode}/username/{username}")
    suspend fun getOrders(
        @Path("username") username: String,
        @Path("storeCode") storeCode: String
    ): List<OrderResponse>
}
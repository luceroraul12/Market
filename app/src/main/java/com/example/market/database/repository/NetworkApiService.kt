package com.example.market.database.repository

import retrofit2.http.GET

interface NetworkApiService {

    @GET("/customer/products")
    suspend fun getHoroscope(): List<Int>
}
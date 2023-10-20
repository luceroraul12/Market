package com.example.market.database.repository

import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val service: NetworkApiService
) {

    suspend fun example(): List<Int>{
        return service.getHoroscope()
    }
}
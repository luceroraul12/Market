package com.example.market.database.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // altero el request pre enviado
        val request = chain.request().newBuilder()
            .build()
        // Lo vuelvo a setear y que siga su camino
        return chain.proceed(request)
    }


}
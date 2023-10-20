package com.example.market.database

import com.example.market.BuildConfig.BASE_URL
import com.example.market.database.interceptors.AuthInterceptor
import com.example.market.database.repository.NetworkApiService
import com.example.market.database.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun privdeOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworApiService(retrofit: Retrofit): NetworkApiService {
        return retrofit.create(NetworkApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(service: NetworkApiService): NetworkRepository {
        return NetworkRepository(service)
    }

}
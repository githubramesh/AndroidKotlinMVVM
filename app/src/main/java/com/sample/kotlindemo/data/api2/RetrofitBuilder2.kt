package com.sample.kotlindemo.data.api2

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "http://demo8912703.mockable.io/"

@Module
@InstallIn(SingletonComponent::class)
class RetrofitBuilder2 {

    @Provides
    @Singleton
    fun getRetrofitInstance(): ApiService2 {

        val apiService : ApiService2 by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService2::class.java)
        }
        return apiService
    }
}
package com.sample.kotlindemo.coroutine

import retrofit2.http.GET

interface ApiService {
    @GET("users/1") // Example endpoint
    suspend fun getUser(): ApiResponse
}

package com.sample.kotlindemo.data.api2

import com.sample.kotlindemo.data.model.ApiUser
import retrofit2.http.GET

interface ApiService2 {

    @GET("users")
    suspend fun getUsers(): List<ApiUser>

}
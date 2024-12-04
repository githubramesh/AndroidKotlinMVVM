package com.sample.kotlindemo.data.api

import com.sample.kotlindemo.data.model.ApiUser
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    fun getUsers(): Flow<List<ApiUser>>

    fun getMoreUsers(): Flow<List<ApiUser>>

    fun getUsersWithError(): Flow<List<ApiUser>>
}
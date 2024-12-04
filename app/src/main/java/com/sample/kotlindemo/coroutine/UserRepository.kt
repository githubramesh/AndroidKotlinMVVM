package com.sample.kotlindemo.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val apiService: ApiService) {

    suspend fun getUserData(): ApiResponse {
        return withContext(Dispatchers.IO) {
            apiService.getUser()
        }
    }
}

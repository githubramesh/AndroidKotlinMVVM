package com.sample.kotlindemo.coroutine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    // LiveData to hold the user data
    private val _userData = MutableLiveData<ApiResponse>()
    val userData: LiveData<ApiResponse> get() = _userData // Expose immutable LiveData

    // LiveData for errors
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error // Expose immutable LiveData

    fun fetchUserData() {
        viewModelScope.launch {
            try {
                val data = repository.getUserData()
                _userData.value = data // Update LiveData
            } catch (e: Exception) {
                _error.value = e.message // Handle error
            }
        }
    }
}

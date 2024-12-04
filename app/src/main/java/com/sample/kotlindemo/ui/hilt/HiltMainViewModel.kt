package com.sample.kotlindemo.ui.hilt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.kotlindemo.data.api2.ApiService2
import com.sample.kotlindemo.data.model.ApiUser
import com.sample.kotlindemo.ui.base.UiState
import com.sample.kotlindemo.utils.DefaultDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HiltMainViewModel @Inject constructor(
    private val apiService: ApiService2,
) : ViewModel() {
    private val dispatcherProvider = DefaultDispatcherProvider()

    private val _uiState = MutableLiveData<UiState<String>>(UiState.Loading)
    val uiState: LiveData<UiState<String>> = _uiState

    private val _userList = MutableLiveData<UiState<List<ApiUser>>>(UiState.Loading)
    val userList: LiveData<UiState<List<ApiUser>>> = _userList

    var job: Job?= null

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        _uiState.value = UiState.Loading
        job = viewModelScope.launch {
            val response = apiService.getUsers()
            withContext(dispatcherProvider.main) {
                if (response.isNotEmpty()) {
                    _uiState.value = UiState.Success("Task Completed")
                    _userList.value = UiState.Success(response)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
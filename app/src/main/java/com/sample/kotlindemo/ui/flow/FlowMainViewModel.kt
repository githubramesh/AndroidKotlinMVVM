package com.sample.kotlindemo.ui.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.kotlindemo.data.api.ApiHelper
import com.sample.kotlindemo.data.model.ApiUser
import com.sample.kotlindemo.ui.base.UiState
import com.sample.kotlindemo.utils.DispatcherProvider
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlowMainViewModel(
    private val apiHelper: ApiHelper,
    val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val uiState: StateFlow<UiState<String>> = _uiState

    private val _userList = MutableStateFlow<UiState<List<ApiUser>>>(UiState.Loading)
    val userList: StateFlow<UiState<List<ApiUser>>> = _userList

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch(dispatcherProvider.main) {
            _uiState.value = UiState.Loading
            apiHelper.getUsers()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .onCompletion {
                    _uiState.value = UiState.Success("Task Completed")
                }
                .collect {
                    _userList.value = UiState.Success(it)
                }
        }
    }





}
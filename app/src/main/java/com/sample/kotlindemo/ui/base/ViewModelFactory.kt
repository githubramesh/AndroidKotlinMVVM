package com.sample.kotlindemo.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.kotlindemo.data.api.ApiHelper
import com.sample.kotlindemo.ui.flow.FlowMainViewModel
import com.sample.kotlindemo.utils.DispatcherProvider

class ViewModelFactory(
    private val apiHelper: ApiHelper,
    private val dispatcherProvider: DispatcherProvider
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlowMainViewModel::class.java)) {
            return FlowMainViewModel(apiHelper, dispatcherProvider) as T
        }
        /*if (modelClass.isAssignableFrom(Flow2MainViewModel::class.java)) {
            return Flow2MainViewModel(apiHelper, dispatcherProvider) as T
        }*/
        throw IllegalArgumentException("Unknown class name")
    }

}
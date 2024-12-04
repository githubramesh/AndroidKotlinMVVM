package com.sample.kotlindemo.ui.flow

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.kotlindemo.R
import com.sample.kotlindemo.data.api.ApiHelperImpl
import com.sample.kotlindemo.data.api.RetrofitBuilder
import com.sample.kotlindemo.data.model.ApiUser
import com.sample.kotlindemo.ui.adapters.ApiUserAdapter
import com.sample.kotlindemo.ui.base.UiState
import com.sample.kotlindemo.ui.base.ViewModelFactory
import com.sample.kotlindemo.utils.DefaultDispatcherProvider
import kotlinx.coroutines.launch

class FlowMainActivity : AppCompatActivity() {

    private lateinit var viewModel: FlowMainViewModel
    private var recyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var textView: TextView? = null
    private lateinit var adapter: ApiUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow_main)

        setupUI()
        setupViewModel()
        setupUiStateObserver()
        setupUserListObserver()
    }

    private fun setupUI() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        textView = findViewById(R.id.textView)

        recyclerView?.layoutManager = LinearLayoutManager(this)
        adapter = ApiUserAdapter(arrayListOf())

        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                recyclerView?.context,
                (recyclerView?.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView?.adapter = adapter
    }

    private fun setupUiStateObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            progressBar?.visibility = View.GONE
                            textView?.text = it.data
                            textView?.visibility = View.VISIBLE
                        }

                        is UiState.Loading -> {
                            progressBar?.visibility = View.VISIBLE
                            textView?.visibility = View.GONE
                        }

                        is UiState.Error -> {
                            //Handle Error
                            progressBar?.visibility = View.GONE
                            Toast.makeText(this@FlowMainActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun setupUserListObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userList.collect {
                    when (it) {
                        is UiState.Success -> {
                            progressBar?.visibility = View.GONE
                            renderList(it.data)
                            recyclerView?.visibility = View.VISIBLE
                        }

                        is UiState.Loading -> {
                            progressBar?.visibility = View.VISIBLE
                            recyclerView?.visibility = View.GONE
                            textView?.visibility = View.GONE
                        }

                        is UiState.Error -> {
                            //Handle Error
                            progressBar?.visibility = View.GONE
                            Toast.makeText(this@FlowMainActivity, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(users: List<ApiUser>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService),
                DefaultDispatcherProvider()
            )
        )[FlowMainViewModel::class.java]
    }
}



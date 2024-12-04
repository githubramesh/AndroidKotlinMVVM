package com.sample.kotlindemo.ui.hilt

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.kotlindemo.data.model.ApiUser
import com.sample.kotlindemo.databinding.ActivityHiltMainBinding
import com.sample.kotlindemo.ui.adapters2.ApiUser2Adapter
import com.sample.kotlindemo.ui.base.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HiltMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHiltMainBinding
    private lateinit var viewModel: HiltMainViewModel
    private lateinit var adapter: ApiUser2Adapter
    //@Inject lateinit var analytics: ApiUser2Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHiltMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupViewModel()
        setupUiStateObserver()
        setupUserListObserver()
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ApiUser2Adapter(arrayListOf())

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
    }

    private fun setupUiStateObserver() {
        viewModel.uiState.observe(this) {
            when (it) {
                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.textView.text = it.data
                    binding.textView.visibility = View.VISIBLE
                }

                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.textView.visibility = View.GONE
                }

                is UiState.Error -> {
                    //Handle Error
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@HiltMainActivity, it.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun setupUserListObserver() {
        viewModel.userList.observe(this) {
            when (it) {
                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    renderList(it.data)
                    binding.recyclerView.visibility = View.VISIBLE
                }

                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.textView.visibility = View.GONE
                }

                is UiState.Error -> {
                    //Handle Error
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@HiltMainActivity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun renderList(users: List<ApiUser>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[HiltMainViewModel::class.java]
    }
}



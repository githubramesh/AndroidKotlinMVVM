package com.sample.kotlindemo.coroutine

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
//import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.sample.kotlindemo.R


class CoroutineMainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    /*private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(UserRepository(RetrofitInstance.api))
    }*/

    private val viewModelFactory = UserViewModelFactory(UserRepository(RetrofitInstance.api))
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_coroutine_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        userViewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)

        textView = findViewById(R.id.userDetailsTextView)

        // Observe LiveData
        userViewModel.userData.observe(this, Observer { user ->
            user?.let {
                textView.text = "Id: ${it.id}, User: ${it.name}, Email: ${it.email}"
            }
        })

        userViewModel.error.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                textView.text = "Error: $it"
            }
        })

        // Fetch user data
        userViewModel.fetchUserData()

    }
}




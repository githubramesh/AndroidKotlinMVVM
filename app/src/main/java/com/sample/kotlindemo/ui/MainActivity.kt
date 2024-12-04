package com.sample.kotlindemo.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sample.kotlindemo.R
import com.sample.kotlindemo.coroutine.CoroutineMainActivity
import com.sample.kotlindemo.ui.flow.FlowMainActivity
import com.sample.kotlindemo.ui.hilt.HiltMainActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.kotlinCoroutineButton).setOnClickListener {
            val intent = Intent(this, CoroutineMainActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.kotlinFlowButton).setOnClickListener {
            val intent = Intent(this, FlowMainActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.kotlinHiltButton).setOnClickListener {
            val intent = Intent(this, HiltMainActivity::class.java)
            startActivity(intent)
        }
    }
}




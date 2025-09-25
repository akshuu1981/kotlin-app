package com.akshat.mykotlinapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akshat.mykotlinapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater) // 1
        setContentView(binding.root) // 2


//        setContentView(R.layout.activity_main)
//
//        val mainTextView = findViewById<TextView>(R.id.mainTextView)
        binding.mainTextView.text = "Hello educative.io"
        intent = Intent(this, BlogDetailsActivity::class.java)
        startActivity(intent)

    }
}
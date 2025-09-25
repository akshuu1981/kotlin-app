package com.akshat.mykotlinapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.akshat.mykotlinapp.databinding.ActivityMainBinding
import com.akshat.mykotlinapp.ui.theme.MyKotlinAppTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater) // 1
        setContentView(binding.root) // 2


//        setContentView(R.layout.activity_main)
//
//        val mainTextView = findViewById<TextView>(R.id.mainTextView)
        binding.mainTextView.text = "Hello educative.io"
    }
}
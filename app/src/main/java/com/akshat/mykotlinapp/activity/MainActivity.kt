package com.akshat.mykotlinapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshat.mykotlinapp.BlogHttpClient
import com.akshat.mykotlinapp.R
import com.akshat.mykotlinapp.adapter.MainAdapter
import com.akshat.mykotlinapp.databinding.ActivityMainBinding
import com.akshat.mykotlinapp.datamodel.Blog
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val adapter = MainAdapter() // 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater) // 1
        setContentView(binding.root) // 2

        binding.recyclerView.layoutManager = LinearLayoutManager(this) // 2
        binding.recyclerView.adapter = adapter // 3

        loadData()

    }

    fun loadData() {
        BlogHttpClient.loadBlogArticles(
            onSuccess = { blogList: List<Blog> ->
                runOnUiThread {
                    adapter.submitList(blogList)
                }
            },
            onError = {
                runOnUiThread {
                    showErrorSnackbar()
                }
            }
        )
    }

    fun showErrorSnackbar(){
        Snackbar.make(binding.root,
            "Error during loading blog articles", Snackbar.LENGTH_INDEFINITE).run {
            setActionTextColor(getColor(R.color.orange500))
            setAction("Retry") {
                loadData()
                dismiss()
            }
        }.show()
    }
}
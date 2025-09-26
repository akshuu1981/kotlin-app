package com.akshat.mykotlinapp.repository

import android.content.Context
import com.akshat.mykotlinapp.BlogHttpClient
import com.akshat.mykotlinapp.database.DatabaseProvider
import com.akshat.mykotlinapp.datamodel.Blog
import java.util.concurrent.Executors

class BlogRepository(context: Context) {

    private val httpClient = BlogHttpClient
    private val database = DatabaseProvider.getInstance(context.applicationContext)
    private val executor = Executors.newSingleThreadExecutor()

    fun loadDataFromDatabase(callback: (List<Blog>) -> Unit) {
        executor.execute {
            callback(database.blogDao().getAll())
        }
    }

    fun loadDataFromNetwork(onSuccess: (List<Blog>) -> Unit, onError: () -> Unit) {
        executor.execute {
            val blogList = httpClient.loadBlogArticles()
            if (blogList == null) {
                onError() // 1
            } else {
                val blogDAO = database.blogDao()
                blogDAO.deleteAll() // 2
                blogDAO.insertAll(blogList) // 3
                onSuccess(blogList) // 4
            }
        }
    }
}
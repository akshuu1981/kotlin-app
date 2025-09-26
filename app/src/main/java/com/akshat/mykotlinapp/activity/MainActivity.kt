package com.akshat.mykotlinapp.activity

import android.content.DialogInterface
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshat.mykotlinapp.BlogHttpClient
import com.akshat.mykotlinapp.R
import com.akshat.mykotlinapp.adapter.MainAdapter
import com.akshat.mykotlinapp.databinding.ActivityMainBinding
import com.akshat.mykotlinapp.datamodel.Blog
import com.akshat.mykotlinapp.repository.BlogRepository
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val repository by lazy { BlogRepository(applicationContext) } // 1

    private val adapter = MainAdapter { blog ->
        BlogDetailsActivity.start(this, blog)
    }

    companion object {
        private const val SORT_TITLE = 0 // 1
        private const val SORT_DATE = 1 // 1
    }

    private var currentSort = SORT_DATE // 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater) // 1
        setContentView(binding.root) // 2

        binding.toolbarMenu.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.sort) {
                onSortClicked()
            }
            false
        }

        val searchItem = binding.toolbarMenu.menu.findItem(R.id.search) // 1
        val searchView = searchItem.actionView as SearchView // 2
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener { // 3
            override fun onQueryTextSubmit(query: String): Boolean = false

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter(newText) // 4
                return true
            }
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(this) // 2
        binding.recyclerView.adapter = adapter // 3
        binding.refresh.setOnRefreshListener { // 1
            loadDataFromNetwork()
        }
        loadDataFromDatabase()
        loadDataFromNetwork()

    }

    private fun loadDataFromNetwork() {
        binding.refresh.isRefreshing = true // 1
        repository.loadDataFromNetwork (
                onSuccess = { blogList: List<Blog> ->
                    runOnUiThread {
                        binding.refresh.isRefreshing = false
                        adapter.setData(blogList)
                        sortData()
                    }
                },
            onError = {
                binding.refresh.isRefreshing = false
                showErrorSnackbar()
            }
        )
    }

    private fun loadDataFromDatabase() {
        repository.loadDataFromDatabase { blogList: List<Blog> ->
            runOnUiThread {
                adapter.setData(blogList)
                sortData()
            }
        }
    }

    fun showErrorSnackbar(){
        Snackbar.make(binding.root,
            "Error during loading blog articles", Snackbar.LENGTH_INDEFINITE).run {
            setActionTextColor(getColor(R.color.orange500))
            setAction("Retry") {
                loadDataFromNetwork()
                dismiss()
            }
        }.show()
    }

    private fun onSortClicked() {
        val items = arrayOf("Title", "Date")
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.sort_title))
            .setSingleChoiceItems(items, currentSort) { dialog: DialogInterface, which: Int ->
                dialog.dismiss()
                currentSort = which
                sortData()
            }.show()
    }

    private fun sortData() {
        if (currentSort == SORT_TITLE) {
            adapter.sortByTitle() // implemented later in this lesson
        } else if (currentSort == SORT_DATE) {
            adapter.sortByDate() // implemented later in this lesson
        }
        binding.recyclerView.scrollToPosition(0)
    }
}
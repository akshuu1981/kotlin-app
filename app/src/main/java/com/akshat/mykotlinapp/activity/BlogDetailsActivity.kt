package com.akshat.mykotlinapp.activity

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.akshat.mykotlinapp.BlogHttpClient
import com.akshat.mykotlinapp.R
import com.akshat.mykotlinapp.databinding.ActivityBlogDetailsBinding
import com.akshat.mykotlinapp.datamodel.Blog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar


class BlogDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBlogDetailsBinding // 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBlogDetailsBinding.inflate(layoutInflater) // 1
        setContentView(binding.root) // 2

        binding.imageBack.setOnClickListener { finish() }

        loadData()

//        Glide.with(this)
//            .load(IMAGE_URL)
//            .transition(DrawableTransitionOptions.withCrossFade())
//            .into(binding.imageMain)
//
//        Glide.with(this)
//            .load(AVATAR_URL)
//            .transform(CircleCrop())
//            .transition(DrawableTransitionOptions.withCrossFade())
//            .into(binding.imageAvatar)
    }

    private fun loadData() {
        binding.progressBar.visibility = View.VISIBLE

        BlogHttpClient.loadBlogArticles( // 1
            onSuccess = { list: List<Blog> ->
                Log.i("BlogDetailsActivity", "data = $list") // 2
                runOnUiThread {
                    showData(list[0])
                } // 3
            },
            onError = {
                // handle error
                runOnUiThread { showErrorSnackbar() }
            }
        )
    }

    private fun showErrorSnackbar() {
        Snackbar.make(binding.root,
            "Error during loading blog articles", Snackbar.LENGTH_INDEFINITE).run {
            setActionTextColor(getColor(R.color.orange500))
            setAction("Retry") {
                loadData()
                dismiss()
            }
        }.show()
    }

    private fun showData(blog: Blog) {
        binding.progressBar.visibility = View.INVISIBLE
        binding.textTitle.text = blog.title
        binding.textDate.text = blog.date
        binding.textAuthor.text = blog.author.name
        binding.textRating.text = blog.rating.toString()
        binding.textViews.text = String.format("(%d views)", blog.views)
        binding.textDescription.text = Html.fromHtml(blog.description)
        binding.ratingBar.rating = blog.rating

        Glide.with(this)
            .load(blog.image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imageMain)
        Glide.with(this)
            .load(blog.author.avatar)
            .transform(CircleCrop())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imageAvatar)
    }

}
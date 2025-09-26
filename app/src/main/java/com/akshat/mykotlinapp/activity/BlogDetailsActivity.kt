package com.akshat.mykotlinapp.activity

import android.app.Activity
import android.content.Intent
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


    companion object {
        private const val EXTRAS_BLOG = "EXTRAS_BLOG"

        fun start(activity: Activity, blog: Blog) {
            val intent = Intent(activity, BlogDetailsActivity::class.java)
            intent.putExtra(EXTRAS_BLOG, blog)
            activity.startActivity(intent)
        }
    }
    private lateinit var binding: ActivityBlogDetailsBinding // 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBlogDetailsBinding.inflate(layoutInflater) // 1
        setContentView(binding.root) // 2

        binding.imageBack.setOnClickListener { finish() }

        intent.extras?.getParcelable<Blog>(EXTRAS_BLOG)?.let { blog ->
            showData(blog)
        }
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
        Log.d("BlogDetailsActivity", "showData: $blog")

        Glide.with(this)
            .load(blog.getImageUrl())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imageMain)
        Glide.with(this)
            .load(blog.author.getAvatarUrl())
            .transform(CircleCrop())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imageAvatar)
    }

}
package com.akshat.mykotlinapp

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

private const val KEY_LOGIN_STATE = "key_login_state"

public const val IMAGE_URL = "https://bitbucket.org/dmytrodanylyk/travel-blog-resources/raw/" +
        "3436e16367c8ec2312a0644bebd2694d484eb047/images/sydney_image.jpg"
public const val AVATAR_URL = "https://bitbucket.org/dmytrodanylyk/travel-blog-resources/raw/" +
        "3436e16367c8ec2312a0644bebd2694d484eb047/avatars/avatar1.jpg"

class BlogPreferences(context: Context) {
    private val preferences: SharedPreferences
            = context.getSharedPreferences("travel-blog", Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean = preferences.getBoolean(KEY_LOGIN_STATE, false)

    fun setLoggedIn(loggedIn: Boolean) {
        preferences.edit { putBoolean(KEY_LOGIN_STATE, loggedIn) }
    }
}
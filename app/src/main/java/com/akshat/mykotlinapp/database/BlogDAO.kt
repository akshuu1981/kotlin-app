package com.akshat.mykotlinapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.akshat.mykotlinapp.datamodel.Blog

@Dao
interface BlogDAO {

    @Query("SELECT * FROM blog")
    fun getAll(): List<Blog> // 1

    @Insert
    fun insertAll(blogList: List<Blog>) // 2

    @Query("DELETE FROM blog")
    fun deleteAll() // 3
}
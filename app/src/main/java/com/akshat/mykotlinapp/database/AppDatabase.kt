package com.akshat.mykotlinapp.database

import androidx.room.RoomDatabase
import androidx.room.Database
import com.akshat.mykotlinapp.datamodel.Blog

@Database(entities = [Blog::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun blogDao(): BlogDAO
}
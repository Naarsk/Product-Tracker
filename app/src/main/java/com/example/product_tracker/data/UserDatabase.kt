package com.example.product_tracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.product_tracker.model.User
import com.example.product_tracker.util.Converters

@Database(entities = [User::class], version = 1)

@TypeConverters(Converters::class)

abstract class UserDatabase : RoomDatabase() {
    companion object{
        const val NAME = "User_DB"
    }

    abstract fun getUserDao() : UserDao
}
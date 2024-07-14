package com.example.product_tracker

import android.app.Application
import androidx.room.Room.databaseBuilder
import com.example.product_tracker.data.AppDatabase
import com.example.product_tracker.data.UserDatabase

class MainApplication : Application(){
    companion object{
        lateinit var userDatabase: UserDatabase
        lateinit var appDatabase: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        userDatabase = databaseBuilder(
            applicationContext,
            UserDatabase :: class.java,
            UserDatabase.NAME
        ).build()

        appDatabase = databaseBuilder(
            applicationContext,
            AppDatabase :: class.java,
            AppDatabase.NAME
        ).build()
    }

}
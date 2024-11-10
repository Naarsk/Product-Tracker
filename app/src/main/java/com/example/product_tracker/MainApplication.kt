package com.example.product_tracker

import android.app.Application
import android.util.Log
import androidx.room.Room.databaseBuilder
import com.example.product_tracker.data.AppDatabase
import com.example.product_tracker.data.ProductDao
import com.example.product_tracker.data.SaleDao
import com.example.product_tracker.data.UserDao
import com.example.product_tracker.data.UserDatabase

class MainApplication : Application() {
    // DAOs for accessing product and sale data


    companion object {
        // Database instances for user and app data
        lateinit var userDatabase: UserDatabase
        lateinit var appDatabase: AppDatabase
        // DAOs for accessing product and sale data
        lateinit var productDao: ProductDao
        lateinit var saleDao: SaleDao
        lateinit var userDao: UserDao
    }

    override fun onCreate() {
        super.onCreate()

        // Initialize user database
        Log.d("MainApplication", "Initializing user database")
        userDatabase = databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            UserDatabase.NAME
        ).build()
        Log.d("MainApplication", "User database initialized")

        // Initialize app database
        Log.d("MainApplication", "Initializing app database")
        appDatabase = databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.NAME
        ).build()
        Log.d("MainApplication", "App database initialized")

        // Get DAOs from app database
        productDao = appDatabase.getProductDao()
        saleDao = appDatabase.getSaleDao()
        userDao = userDatabase.getUserDao()
        Log.d("MainApplication", "DAOs initialized")
    }
}
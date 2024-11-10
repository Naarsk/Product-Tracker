package com.example.product_tracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.product_tracker.model.Product
import com.example.product_tracker.model.Sale
import com.example.product_tracker.util.Converters

@Database(entities = [Product::class, Sale::class], version = 1)

@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {

    companion object{
        const val NAME = "App_DB"
    }

    abstract fun getProductDao(): ProductDao
    abstract fun getSaleDao(): SaleDao
}
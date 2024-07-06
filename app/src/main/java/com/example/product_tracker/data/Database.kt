package com.example.product_tracker.data

import Product
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.product_tracker.model.Sale

@Database(entities = [Product::class, Sale::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun saleDao(): SaleDao
}
package com.example.product_tracker.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


@Suppress("unused")
class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE " + DatabaseSchema.ProductTable.TABLE_NAME + " (" +
                DatabaseSchema.ProductTable.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseSchema.ProductTable.COLUMN_ID + " TEXT, " +
                DatabaseSchema.ProductTable.COLUMN_NAME + " TEXT, " +
                DatabaseSchema.ProductTable.COLUMN_PRICE + " REAL, " +
                DatabaseSchema.ProductTable.COLUMN_QUANTITY + " INTEGER, " +
                DatabaseSchema.ProductTable.COLUMN_IMAGE_URL + " TEXT, " +
                DatabaseSchema.ProductTable.COLUMN_COLOR + " TEXT, " +
                DatabaseSchema.ProductTable.COLUMN_CREATED_AT + " TEXT, " +
                DatabaseSchema.ProductTable.COLUMN_UPDATED_AT + " TEXT, " +
                DatabaseSchema.ProductTable.COLUMN_TYPE + " TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database schema changes here
    }

    companion object {
        private const val DATABASE_NAME = "product_tracker_database.db"
        private const val DATABASE_VERSION = 1
    }
}


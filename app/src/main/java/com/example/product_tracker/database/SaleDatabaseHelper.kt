package com.example.product_tracker.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.text.SimpleDateFormat
import android.util.Log
import com.example.product_tracker.model.Sale
import java.util.Date
import java.util.Locale


@Suppress("unused")
class SaleDatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        Log.d("DatabaseHelper", "Creating database: ${DatabaseSchema.ProductTable.TABLE_NAME}")

        val createTableQuery = "CREATE TABLE " + DatabaseSchema.SaleTable.TABLE_NAME + " (" +
                DatabaseSchema.SaleTable.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseSchema.SaleTable.PRODUCT_KEY + " INTEGER, " +
                DatabaseSchema.SaleTable.COLUMN_QUANTITY_SOLD + " INTEGER, " +
                DatabaseSchema.SaleTable.COLUMN_DATE + " TEXT, " +
                DatabaseSchema.SaleTable.COLUMN_PRICE_SOLD + " TEXT, " +
                DatabaseSchema.SaleTable.COLUMN_DISCOUNT + " REAL)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database schema changes here
    }
   
    fun addSaleToDatabase(sale: Sale): Long {
        val db = writableDatabase
        val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        val values = ContentValues()
        values.put("price_sold", sale.price)
        values.put("quantity_sold", sale.quantity)
        values.put("date", sale.date.toString())
        values.put("created_at", currentTime)
        values.put("updated_at", currentTime)

        val newRowId = db.insert("sales_table", null, values)

        if (newRowId != -1L) {
            Log.d(ContentValues.TAG, "New product inserted successfully in row: $newRowId")
        } else {
            Log.e(ContentValues.TAG, "Failed to insert new product")
        }
        return newRowId
    }
    
    companion object {
        const val DATABASE_NAME = "sale_database.db"
        private const val DATABASE_VERSION = 1
    }
}


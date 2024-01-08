package com.example.product_tracker.database

import android.annotation.SuppressLint
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
class SaleDatabaseHelper(private val context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        Log.d("DatabaseHelper", "Creating database: ${DatabaseSchema.ProductTable.TABLE_NAME}")

        val createTableQuery = "CREATE TABLE " + DatabaseSchema.SaleTable.TABLE_NAME + " (" +
            DatabaseSchema.SaleTable.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseSchema.SaleTable.PRODUCT_KEY + " INTEGER, " +
            DatabaseSchema.SaleTable.COLUMN_QUANTITY_SOLD + " INTEGER, " +
            DatabaseSchema.SaleTable.COLUMN_DATE + " TEXT, " +
            DatabaseSchema.SaleTable.COLUMN_PRICE_SOLD + " TEXT, " +
            DatabaseSchema.SaleTable.COLUMN_CREATED_AT + " TEXT, " +
            DatabaseSchema.SaleTable.COLUMN_UPDATED_AT + " TEXT, " +
            DatabaseSchema.SaleTable.COLUMN_DISCOUNT + " REAL)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database schema changes here
    }
    @SuppressLint("Range")
    fun getSalesForDay(input_date: Date): List<Sale> {

        val db = readableDatabase
        val salesForDate = mutableListOf<Sale>()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateString = dateFormat.format(input_date)

        val projection = arrayOf(
            DatabaseSchema.SaleTable.PRODUCT_KEY,
            DatabaseSchema.SaleTable.COLUMN_QUANTITY_SOLD,
            DatabaseSchema.SaleTable.COLUMN_DATE,
            DatabaseSchema.SaleTable.COLUMN_PRICE_SOLD
        )

        val selection = "strftime('%Y-%m-%d', ${DatabaseSchema.SaleTable.COLUMN_DATE}) = strftime('%Y-%m-%d', ?)"
        val selectionArgs = arrayOf(dateString)
        Log.d("SaleDatabaseHelper", "Querying for sales in day: $dateString")

        val cursor = db.query(
            DatabaseSchema.SaleTable.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {

            val productId = cursor.getInt(cursor.getColumnIndex(DatabaseSchema.SaleTable.PRODUCT_KEY))
            val quantitySold = cursor.getInt(cursor.getColumnIndex(DatabaseSchema.SaleTable.COLUMN_QUANTITY_SOLD))
            val saleDateStr = cursor.getString(cursor.getColumnIndex(DatabaseSchema.SaleTable.COLUMN_DATE))
            val priceSoldStr = cursor.getString(cursor.getColumnIndex(DatabaseSchema.SaleTable.COLUMN_PRICE_SOLD))
            Log.d("SaleDatabaseHelper", "Sale found. Date:$saleDateStr, Quantity: $quantitySold, Product row: $productId")

            val saleDate = dateFormat.parse(saleDateStr)
            val priceSold = priceSoldStr.toDouble()
            val product = ProductDatabaseHelper(context).getProductById(productId)
            if (product != null) {
                val sale = Sale(product, quantitySold, priceSold, saleDate)
                salesForDate.add(sale)
                Log.d("SaleDatabaseHelper", "Sale retrieved correctly: $sale")
            } else {
                Log.d("SaleDatabaseHelper", "Impossible to find sale because of missing product for row: $productId")
            }
        }

        cursor.close()
        return salesForDate

    }


    fun addSaleToDatabase(sale: Sale): Long {
        val db = writableDatabase
        val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val saleDateStr = dateFormat.format(sale.date)
        val productKey = ProductDatabaseHelper(context).getProductKey(sale.product)

        val values = ContentValues()
        values.put("product_key", productKey)
        values.put("price_sold", sale.price)
        values.put("discount_amount", sale.product.price - sale.price)
        values.put("quantity_sold", sale.quantity)
        values.put("sale_date", saleDateStr)
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


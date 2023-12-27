package com.example.product_tracker.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.product_tracker.Utils
import com.example.product_tracker.model.Product


@Suppress("unused")
class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        Log.d("DatabaseHelper", "Creating database: ${DatabaseSchema.ProductTable.TABLE_NAME}")

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
    fun getProductsByType(productType: String): ArrayList<Product> {
        Log.d("DatabaseHelper", "Query for type: $productType")
        val productList = ArrayList<Product>()
        val db = readableDatabase
        val projection = arrayOf(
            DatabaseSchema.ProductTable.COLUMN_ID,
            DatabaseSchema.ProductTable.COLUMN_NAME,
            DatabaseSchema.ProductTable.COLUMN_PRICE,
            DatabaseSchema.ProductTable.COLUMN_QUANTITY,
            DatabaseSchema.ProductTable.COLUMN_IMAGE_URL,
            DatabaseSchema.ProductTable.COLUMN_COLOR,
            DatabaseSchema.ProductTable.COLUMN_CREATED_AT,
            DatabaseSchema.ProductTable.COLUMN_UPDATED_AT,
            DatabaseSchema.ProductTable.COLUMN_TYPE
        )
        val selection = "${DatabaseSchema.ProductTable.COLUMN_TYPE} = ?"
        val selectionArgs = arrayOf(productType)
        val sortOrder = "${DatabaseSchema.ProductTable.COLUMN_NAME} ASC"

        val cursor = db.query(
            DatabaseSchema.ProductTable.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getString(getColumnIndexOrThrow(DatabaseSchema.ProductTable.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(DatabaseSchema.ProductTable.COLUMN_NAME))
                val price = getDouble(getColumnIndexOrThrow(DatabaseSchema.ProductTable.COLUMN_PRICE))
                val quantity = getInt(getColumnIndexOrThrow(DatabaseSchema.ProductTable.COLUMN_QUANTITY))
                val imageUrl = getString(getColumnIndexOrThrow(DatabaseSchema.ProductTable.COLUMN_IMAGE_URL))
                val color = getString(getColumnIndexOrThrow(DatabaseSchema.ProductTable.COLUMN_COLOR))
                val createdAt = getString(getColumnIndexOrThrow(DatabaseSchema.ProductTable.COLUMN_CREATED_AT))
                val updatedAt = getString(getColumnIndexOrThrow(DatabaseSchema.ProductTable.COLUMN_UPDATED_AT))
                val type = getString(getColumnIndexOrThrow(DatabaseSchema.ProductTable.COLUMN_TYPE))

                val product = Product(id, type, imageUrl, price, quantity,  color)
                productList.add(product)
            }
        }

        cursor.close()
        Log.d("DatabaseHelper", "Found: ${productList.size} products")
        return productList
    }
    fun addProductsToDatabase(productList: ArrayList<Product>): Int {
        val db = writableDatabase
        val currentTime = Utils().getCurrentTime()
        var addedEntries = 0

        for (product in productList) {
            val values = ContentValues().apply {
                put(DatabaseSchema.ProductTable.COLUMN_ID, product.id)
                put(DatabaseSchema.ProductTable.COLUMN_NAME, product.type + " " + product.color)
                put(DatabaseSchema.ProductTable.COLUMN_PRICE, product.price)
                put(DatabaseSchema.ProductTable.COLUMN_QUANTITY, product.quantity)
                put(DatabaseSchema.ProductTable.COLUMN_IMAGE_URL, product.imageUrl)
                put(DatabaseSchema.ProductTable.COLUMN_COLOR, product.color)
                put(DatabaseSchema.ProductTable.COLUMN_CREATED_AT, currentTime)
                put(DatabaseSchema.ProductTable.COLUMN_UPDATED_AT, currentTime)
                put(DatabaseSchema.ProductTable.COLUMN_TYPE, product.type)
            }
            val newRowId = db.insert(DatabaseSchema.ProductTable.TABLE_NAME, null, values)
            if (newRowId != -1L) {
                addedEntries++
            }
        }
        return addedEntries
    }
    fun getMissingProductIds(productIds: List<String>): List<String> {
        val db = readableDatabase
        val selection = "${DatabaseSchema.ProductTable.COLUMN_ID} IN (${productIds.joinToString()})"
        val query = "SELECT ${DatabaseSchema.ProductTable.COLUMN_ID} FROM ${DatabaseSchema.ProductTable.TABLE_NAME} WHERE $selection"
        val cursor = db.rawQuery(query, null)
        val missingProductIds = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val id = getString(getColumnIndexOrThrow(DatabaseSchema.ProductTable.COLUMN_ID))
                missingProductIds.add(id)
            }
        }
        cursor.close()
        return missingProductIds
    }
    companion object {
        const val DATABASE_NAME = "product_tracker_database.db"
        private const val DATABASE_VERSION = 1
    }
}


package com.example.product_tracker.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.text.SimpleDateFormat
import android.util.Log
import com.example.product_tracker.model.Product
import java.util.Date
import java.util.Locale


@Suppress("unused")
class ProductDatabaseHelper(context: Context?) :
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
    fun getProductsByType(productType: String?): ArrayList<Product> {
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
        val selection = if (productType != null) {
            "${DatabaseSchema.ProductTable.COLUMN_TYPE} = ?"
        } else {
            null
        }
        val selectionArgs = if (productType != null) {
            arrayOf(productType)
        } else {
            null
        }
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

                val product = Product(id, type, imageUrl, price, quantity, color)
                productList.add(product)
            }
        }

        cursor.close()
        Log.d("DatabaseHelper", "Found: ${productList.size} products")
        return productList
    }
    fun addProductToDatabase(product: Product): Long {
        val db = writableDatabase
        val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        val values = ContentValues()
        values.put("price", product.price)
        values.put("type", product.type)
        values.put("color", product.color)
        values.put("quantity", product.quantity)
        values.put("image_url", product.imagePath)
        values.put("id", product.id)
        values.put("name", product.type + " " + product.id)
        values.put("created_at", currentTime)
        values.put("updated_at", currentTime)

        val newRowId = db.insert("product_table", null, values)

        if (newRowId != -1L) {
            Log.d(ContentValues.TAG, "New product inserted successfully in row: $newRowId")
        } else {
            Log.e(ContentValues.TAG, "Failed to insert new product")
        }
        return newRowId
    }
    fun addProductsToDatabase(productList: ArrayList<Product>): Int {
        var addedEntries = 0
        for (product in productList) {
            val newRowId = addProductToDatabase(product)
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
    fun deleteProduct(product: Product): Boolean {
        val db = writableDatabase
        val whereClause = "${DatabaseSchema.ProductTable.COLUMN_ID} = ? AND " +
                "${DatabaseSchema.ProductTable.COLUMN_TYPE} = ? AND " +
                "${DatabaseSchema.ProductTable.COLUMN_IMAGE_URL} = ? AND " +
                "${DatabaseSchema.ProductTable.COLUMN_PRICE} = ? AND " +
                "${DatabaseSchema.ProductTable.COLUMN_QUANTITY} = ? AND " +
                "${DatabaseSchema.ProductTable.COLUMN_COLOR} = ?"
        val whereArgs = arrayOf(
            product.id,
            product.type,
            product.imagePath,
            product.price.toString(),
            product.quantity.toString(),
            product.color
        )
        val deletedRows = db.delete(DatabaseSchema.ProductTable.TABLE_NAME, whereClause, whereArgs)
        db.close()
        return deletedRows > 0
    }

    fun updateProductQuantity(product: Product, updatedQuantity: Int): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put(DatabaseSchema.ProductTable.COLUMN_QUANTITY, updatedQuantity)

        val whereClause = "${DatabaseSchema.ProductTable.COLUMN_ID} = ? AND " +
                "${DatabaseSchema.ProductTable.COLUMN_TYPE} = ? AND " +
                "${DatabaseSchema.ProductTable.COLUMN_IMAGE_URL} = ? AND " +
                "${DatabaseSchema.ProductTable.COLUMN_PRICE} = ? AND " +
                "${DatabaseSchema.ProductTable.COLUMN_QUANTITY} = ? AND " +
                "${DatabaseSchema.ProductTable.COLUMN_COLOR} = ?"

        val whereArgs = arrayOf(
            product.id,
            product.type,
            product.imagePath,
            product.price.toString(),
            product.quantity.toString(),
            product.color
        )

        val updatedRows = db.update(DatabaseSchema.ProductTable.TABLE_NAME, values, whereClause, whereArgs)
        db.close()

        return updatedRows > 0
    }

    companion object {
        const val DATABASE_NAME = "product_database.db"
        private const val DATABASE_VERSION = 1
    }
}


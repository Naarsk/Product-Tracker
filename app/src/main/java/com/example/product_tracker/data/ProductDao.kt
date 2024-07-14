package com.example.product_tracker.data // Adjust package name if needed

import Product
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Handle conflicts (e.g., replace on duplicate)
    fun insertProduct(product: Product)

    @Delete
    fun deleteProduct(id: Int)

    @Query("SELECT * FROM products")
    fun getAllProduct(): LiveData<List<Product>>

    @Query("SELECT * FROM products WHERE id = :productId")
    fun getProductById(productId: Int): Product?

    @Query("SELECT QUANTITY FROM products WHERE id = :productId")
    fun getProductQuantity(productId: Int): Int

    @Query("SELECT * FROM products WHERE type = :productType")
    fun getProductByType(productType: String?): LiveData<List<Product>>

    @Query("SELECT id FROM products WHERE id IN (:productIds)")
    fun getAllProductId(productIds: List<String>): LiveData<List<String>>

    @Query("UPDATE products SET quantity = :updatedQuantity WHERE id = :productId")
    suspend fun updateProductQuantity(productId: Int, updatedQuantity: Int)
}
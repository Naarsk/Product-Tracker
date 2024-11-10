package com.example.product_tracker.data // Adjust package name if needed

import Product
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.Date

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Handle conflicts (e.g., replace on duplicate)
    fun insertProduct(product: Product): Long

    @Query("DELETE FROM products WHERE id = :productId")
    fun deleteProduct(productId: Int)

    @Query("SELECT * FROM products")
    fun getAllProduct(): LiveData<List<Product>>

    @Query("SELECT * FROM products WHERE id = :productId")
    fun getProductById(productId: Int): Int

    @Query("SELECT QUANTITY FROM products WHERE id = :productId")
    fun getProductQuantity(productId: Int): Int

    @Query("SELECT * FROM products WHERE type = :productType")
    fun getProductByType(productType: String?): Int

    @Query("SELECT id FROM products WHERE id IN (:productIds)")
    fun getAllProductId(productIds: List<String>): Int

    @Query("UPDATE products SET quantity = :updatedQuantity WHERE id = :productId")
    suspend fun updateProductQuantity(productId: Int, updatedQuantity: Int): Int

    @Query("UPDATE products SET updatedAt = :updatedAt WHERE id = :productId")
    suspend fun updateUpdatedAt(productId: Int, updatedAt: Date): Int
}
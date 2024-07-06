package com.example.product_tracker.data // Adjust package name if needed

import Product
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE type = :productType ORDER BY type ASC")
    fun getProductsByType(productType: String?): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProductById(productId: Int): Product?

    @Query("SELECT id FROM products WHERE id IN (:productIds)")
    fun getExistingProductIds(productIds: List<String>): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Handle conflicts (e.g., replace on duplicate)
    suspend fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("UPDATE products SET quantity = :updatedQuantity WHERE id = :productId")
    suspend fun updateProductQuantity(productId: Int, updatedQuantity: Int)
}
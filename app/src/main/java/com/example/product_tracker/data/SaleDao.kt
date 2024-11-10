package com.example.product_tracker.data // Adjust package name if needed

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.product_tracker.model.Sale
import java.util.Date

@Dao
interface SaleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Handle conflicts (e.g., replace on duplicate)
    fun insertSale(sale: Sale): Long

    @Query("DELETE FROM sales WHERE id = :saleId")
    fun deleteSale(saleId: Int) : Int

    @Query("SELECT * FROM sales")
    fun getAllSale() : List<Sale>

    @Query(" SELECT * FROM sales WHERE strftime('%Y-%m-%d', date) = strftime('%Y-%m-%d', :date)")
    fun getSalesForDay(date: Date): List<Sale>

    @Query("SELECT * FROM sales WHERE id = :saleId")
    fun getSaleById(saleId: Int): Int

    @Query("UPDATE sales SET updatedAt = :updatedAt WHERE id = :saleId")
    suspend fun updateUpdatedAt(saleId: Int, updatedAt: Date): Int
}
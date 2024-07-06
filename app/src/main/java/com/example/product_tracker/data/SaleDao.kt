package com.example.product_tracker.data // Adjust package name if needed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.product_tracker.model.Sale
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface SaleDao {

    @Query("""
        SELECT * FROM sales 
        WHERE strftime('%Y-%m-%d', date) = strftime('%Y-%m-%d', :date)
    """)
    fun getSalesForDay(date: Date): Flow<List<Sale>>

    @Insert
    suspend fun insertSale(sale: Sale): Long

    // Add more queries as needed for your specific use cases
}
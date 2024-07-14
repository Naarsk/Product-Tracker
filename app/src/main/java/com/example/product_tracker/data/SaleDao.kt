package com.example.product_tracker.data // Adjust package name if needed

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.product_tracker.model.Sale
import java.util.Date

@Dao
interface SaleDao {

    @Insert
    fun insertSale(sale: Sale)

    @Delete
    fun deleteSale(id : Int)

    @Query("SELECT * FROM USER")
    fun getAllSale() : LiveData<List<Sale>>

    @Query(" SELECT * FROM sales WHERE strftime('%Y-%m-%d', date) = strftime('%Y-%m-%d', :date)")
    fun getSalesForDay(date: Date): LiveData<List<Sale>>

}
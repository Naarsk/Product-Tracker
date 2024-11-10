package com.example.product_tracker.ui.sale_calendar

// TODO: add switch to list view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.product_tracker.R
import com.example.product_tracker.data.SaleDao
import com.example.product_tracker.data.SaleViewModel
import com.example.product_tracker.model.Sale
import java.util.Calendar
import java.util.Date

class SaleCalendarActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var salesRecyclerView: RecyclerView
    private lateinit var saleAdapter: SaleAdapter
    private lateinit var saleDao: SaleDao
    private lateinit var saleViewModel: SaleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale_calendar)

        calendarView = findViewById(R.id.calendarView)
        salesRecyclerView = findViewById(R.id.salesRecyclerView)

        // Set up the RecyclerView with an empty list
        saleAdapter = SaleAdapter(emptyList())
        salesRecyclerView.adapter = saleAdapter
        salesRecyclerView.layoutManager = LinearLayoutManager(this)

        // Refresh sales data for the current date
        val currentDate = Calendar.getInstance().time
        val currentSales = getSalesForDate(currentDate)
        saleAdapter.refreshData(currentSales)

        // Set up the CalendarView listener
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            Log.d("SaleCalendarActivity", "Selected date: $selectedDate")
            val sales = getSalesForDate(selectedDate.time)
            updateSalesList(sales) // Call the function to update the sales list
            Log.d("SaleCalendarActivity", "Sales for selected date: $sales")
        }

    }

    private fun getSalesForDate(date: Date): LiveData<List<Sale>> {
        Log.d("SaleCalendarActivity", "Getting sales for date: $date")
        // Return a list of Sale objects
        val sales = saleDao.getSalesForDay(date)
        Log.d("SaleCalendarActivity", "Sales for date: $sales")
        return sales
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateSalesList(sales: LiveData<List<Sale>>) {
        saleAdapter.sales = sales
        saleAdapter.notifyDataSetChanged()
        Log.d("SaleCalendarActivity", "Updated sales list: $sales")
    }
}
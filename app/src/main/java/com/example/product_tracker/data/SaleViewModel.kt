package com.example.product_tracker.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.product_tracker.MainApplication
import com.example.product_tracker.model.Sale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class SaleViewModel  : ViewModel()  {

    private val saleDao = MainApplication.appDatabase.getSaleDao()
    val saleList : List<Sale> = saleDao.getAllSale()
    
    private val _saleCreationResult = MutableLiveData<Boolean>()
    val saleCreationResult: LiveData<Boolean> = _saleCreationResult

    fun createNewSale(productId: Int, date: Date, price: Double, quantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val sale = Sale(productId = productId, date = date, price = price, quantity = quantity, createdAt = Date.from(Instant.now()),
                updatedAt = Date.from(Instant.now())
            )
            val newRowId = saleDao.insertSale(sale)
            _saleCreationResult.postValue(newRowId != -1L)
        }
    }
}

package com.example.product_tracker.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.product_tracker.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductListViewModel(private val productDao: ProductDao) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    fun getProducts(productType: String) {
        viewModelScope.launch {
            Log.d("ProductListViewModel", "Fetching products of type: $productType")
            val products = withContext(Dispatchers.IO) {
                productDao.getProductByType(productType)
            }
            Log.d("ProductListViewModel", "Products fetched: $products")
            _products.postValue(products)
        }
    }
}
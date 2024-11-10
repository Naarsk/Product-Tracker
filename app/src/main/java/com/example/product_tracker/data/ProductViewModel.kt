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
import java.time.Instant
import java.util.Date

class ProductViewModel(private val productDao : ProductDao) : ViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    fun getProductById(productId : Int) {
        viewModelScope.launch{
            Log.d("ProductListViewModel", "Fetching product with ID: $productId")
            val product = withContext(Dispatchers.IO) {
                productDao.getProductById(productId)
            }
            Log.d("ProductListViewModel", "Product fetched: ${product.code}")
            _product.postValue(product)
        }
    }


    private val _productCreationResult = MutableLiveData<Boolean>()
    val productCreationResult: LiveData<Boolean> = _productCreationResult

    fun createNewProduct(code: String, type: String, imagePath: String, price: Double, quantity: Int, color: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val product = Product(
                code = code,
                type = type,
                imagePath = imagePath,
                price = price,
                quantity = quantity,
                color = color, createdAt = Date.from(Instant.now()),
                updatedAt = Date.from(Instant.now())
            )
            val newRowId = productDao.insertProduct(product)
            _productCreationResult.postValue(newRowId != -1L)
        }
    }

    private val _productUpdateResult = MutableLiveData<Boolean>()
    val productUpdateResult: LiveData<Boolean> = _productUpdateResult

    fun addQuantity(productId : Int, addQuantity : Int) {
        viewModelScope.launch(Dispatchers.IO) { // Run on a background thread

            val oldQuantity = productDao.getProductQuantity(productId = productId)
            val updatedQuantity = oldQuantity + addQuantity

            val updateSuccessful = productDao.updateProductQuantity(productId = productId, updatedQuantity = updatedQuantity)!= 0
            _productUpdateResult.postValue(updateSuccessful)

            if (updateSuccessful) {
                productDao.updateUpdatedAt(
                    productId = productId,
                    updatedAt = Date.from(Instant.now())
                )
            }
        }
    }
}
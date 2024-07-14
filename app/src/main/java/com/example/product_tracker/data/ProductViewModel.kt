package com.example.product_tracker.data

import Product
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.product_tracker.MainApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class ProductViewModel : ViewModel() {

    private val productDao = MainApplication.appDatabase.getProductDao()

    val productList : LiveData<List<Product>> = productDao.getAllProduct()

    fun createNewProduct(code : String, type : String, imagePath : String, price : Double, quantity : Int, color : String){

        viewModelScope.launch(Dispatchers.IO) { // Run on a background thread
            val product = Product(code = code, type = type, imagePath = imagePath, price = price, quantity = quantity, color = color,createdAt = Date.from(Instant.now()), updatedAt = Date.from(Instant.now())
            )
            productDao.insertProduct(product)
        }
    }

    fun deleteProduct(id : Int){
        productDao.deleteProduct(id = id)
    }

    fun addQuantity(id : Int, addQuantity : Int) {
        viewModelScope.launch(Dispatchers.IO) { // Run on a background thread

            val oldQuantity = productDao.getProductQuantity(productId = id)
            val updatedQuantity = oldQuantity + addQuantity

            productDao.updateProductQuantity(productId = id, updatedQuantity = updatedQuantity)
        
        }

    }
}
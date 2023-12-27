package com.example.product_tracker.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.product_tracker.example.ProductDataSource
import com.example.product_tracker.model.Product

class GalleryViewModel : ViewModel() {
    private var productList: MutableLiveData<ArrayList<Product>>? = null
    fun getProductList(productType: String): LiveData<ArrayList<Product>> {
        if (productList == null) {
            productList = MutableLiveData()
            fetchProductList(productType)
        }
        return productList!!
    }

    private fun fetchProductList(productType: String) {
        val products = ProductDataSource.getProductList(productType)
        productList!!.value = products
    }
}
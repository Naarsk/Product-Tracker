package com.example.product_tracker.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.product_tracker.example.ProductDataSource;
import com.example.product_tracker.model.Product;

import java.util.List;

public class GalleryViewModel extends ViewModel {
    private MutableLiveData<List<Product>> productList;

    public LiveData<List<Product>> getProductList(String productType) {
        if (productList == null) {
            productList = new MutableLiveData<>();
            fetchProductList(productType);
        }
        return productList;
    }

    private void fetchProductList(String productType) {
        List<Product> products = ProductDataSource.getProductList(productType);
        productList.setValue(products);
    }
}
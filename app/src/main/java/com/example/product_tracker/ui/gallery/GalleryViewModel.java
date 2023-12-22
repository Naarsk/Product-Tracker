package com.example.product_tracker.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.product_tracker.example.ProductDataSource;
import com.example.product_tracker.model.Product;

import java.util.List;

public class GalleryViewModel extends ViewModel {

    private final MutableLiveData<List<Product>> productList;

    public GalleryViewModel() {
        productList = new MutableLiveData<>();
        fetchProductList();
    }

    public LiveData<List<Product>> getProductList() {
        return productList;
    }

    private void fetchProductList() {
        List<Product> products = ProductDataSource.getProductList();
        productList.setValue(products);
    }
}
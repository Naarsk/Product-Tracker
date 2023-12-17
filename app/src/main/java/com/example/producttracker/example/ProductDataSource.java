package com.example.producttracker.example;

import com.example.producttracker.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDataSource {
    public static List<Product> getProductList() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("bag","bag_1.png", 59.99, 10));
        productList.add(new Product("bag","bag_2.png", 19.99, 20));
        productList.add(new Product("wallet","wallet_1.png", 14.99, 15));
        return productList;
    }

}
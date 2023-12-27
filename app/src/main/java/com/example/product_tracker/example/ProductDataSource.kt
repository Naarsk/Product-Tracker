package com.example.product_tracker.example;

import com.example.product_tracker.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDataSource {
    public static List<Product> getProductList(String type) {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("bag","bag_1.png", 59.99, 10, "brown"));
        productList.add(new Product("bag","bag_2.png", 19.99, 20, "brown"));
        productList.add(new Product("wallet","wallet_1.png", 14.99, 15,"brown"));

        List<Product> filteredList = new ArrayList<>();
        for (Product product : productList) {
            if (product.getType().equals(type)) {
                filteredList.add(product);
            }
        }
        return filteredList;
    }
}
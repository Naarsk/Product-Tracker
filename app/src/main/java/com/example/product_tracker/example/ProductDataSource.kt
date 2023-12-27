package com.example.product_tracker.example

import com.example.product_tracker.model.Product

object ProductDataSource {
    fun getProductList(type: String?): ArrayList<Product> {
        val productList: MutableList<Product> = ArrayList()
        productList.add(
            Product(
                "bag",
                "/storage/1AFF-2019/Pictures/example/bag_1.png",
                59.99,
                10,
                "brown"
            )
        )
        productList.add(
            Product(
                "bag",
                "/storage/1AFF-2019/Pictures/example/bag_2.png",
                19.99,
                20,
                "brown"
            )
        )
        productList.add(
            Product(
                "wallet",
                "/storage/1AFF-2019/Pictures/example/wallet_1.png",
                14.99,
                15,
                "brown"
            )
        )
        val filteredList: ArrayList<Product> = ArrayList()
        for (product in productList) {
            if (product.type == type) {
                filteredList.add(product)
            }
        }
        return filteredList
    }
}
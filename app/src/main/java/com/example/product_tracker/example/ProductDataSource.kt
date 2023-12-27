package com.example.product_tracker.example

import com.example.product_tracker.model.Product

object ProductDataSource {
    fun getProductList(type: String?): ArrayList<Product> {
        val productList: MutableList<Product> = ArrayList()
        productList.add(
            Product(
                "001",
                "bag",
                "/storage/1AFF-2019/Pictures/example/bag_1.png",
                59.99,
                10,
                "black"
            )
        )
        productList.add(
            Product(
                "002",
                "bag",
                "/storage/1AFF-2019/Pictures/example/bag_2.png",
                19.99,
                20,
                "white"
            )
        )
        productList.add(
            Product(
                "003",
                "wallet",
                "/storage/1AFF-2019/Pictures/example/wallet_1.png",
                14.99,
                15,
                "black"
            )
        )

        return if (type != null) {
            val filteredList: ArrayList<Product> = ArrayList()
            for (product in productList) {
                if (product.type == type) {
                    filteredList.add(product)
                }
            }
            filteredList
        } else {
            ArrayList(productList)
        }
    }
}
package com.example.product_tracker.model

import java.io.Serializable

class Product(
    var id: String,
    var type: String,
    var imagePath: String,
    var price: Double,
    var quantity: Int,
    var color: String
) : Serializable
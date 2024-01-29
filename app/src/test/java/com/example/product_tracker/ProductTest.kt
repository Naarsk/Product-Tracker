package com.example.product_tracker

import com.example.product_tracker.model.Product
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class ProductTest {

    private lateinit var product: Product
    private val id = "1"
    private val type = "Bag"
    private val imagePath = "/path/to/image.jpg"
    private val price = 9.99
    private val quantity = 10
    private val color = "Red"

    @Before
    fun setUp() {
        product = Product(id,type,imagePath,price,quantity,color)
    }

    fun tearDown() {
        // Clean up any resources if needed
    }

    @Test
    fun getId() {
        product.id = id
        Assertions.assertEquals(id, product.id)
    }

    @Test
    fun setId() {
        product.id = id
        Assertions.assertEquals(id, product.id)
    }

    @Test
    fun getType() {
        product.type = type
        Assertions.assertEquals(type, product.type)
    }

    @Test
    fun setType() {
        product.type = type
        Assertions.assertEquals(type, product.type)
    }

    @Test
    fun getImagePath() {
        product.imagePath = imagePath
        Assertions.assertEquals(imagePath, product.imagePath)
    }

    @Test
    fun setImagePath() {
        product.imagePath = imagePath
        Assertions.assertEquals(imagePath, product.imagePath)
    }

    @Test
    fun getPrice() {
        product.price = price
        Assertions.assertEquals(price, product.price)
    }

    @Test
    fun setPrice() {
        product.price = price
        Assertions.assertEquals(price, product.price)
    }

    @Test
    fun getQuantity() {
        product.quantity = quantity
        Assertions.assertEquals(quantity, product.quantity)
    }

    @Test
    fun setQuantity() {
        product.quantity = quantity
        Assertions.assertEquals(quantity, product.quantity)
    }

    @Test
    fun getColor() {
        product.color = color
        Assertions.assertEquals(color, product.color)
    }

    @Test
    fun setColor() {
        product.color = color
        Assertions.assertEquals(color, product.color)
    }
}
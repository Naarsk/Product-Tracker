package com.example.product_tracker

import com.example.product_tracker.model.Product
import com.example.product_tracker.model.Sale
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

import java.util.Date

class SaleTest {

    private lateinit var sale: Sale

    private val id = "1"
    private val type = "Bag"
    private val imagePath = "/path/to/image.jpg"
    private val price = 9.99
    private val quantity = 10
    private val color = "Red"
    private var product = Product(id,type,imagePath,price,quantity,color)

    private val quantitySold = 10
    private val priceSold = 9.99
    private val saleDate = Date()

    @Before
    fun setUp() {
        sale = Sale(product, quantitySold, priceSold, saleDate)
    }

    @After
    fun tearDown() {
        // Clean up any resources if needed
    }

    @Test
    fun getProduct() {
        Assertions.assertEquals(product, sale.product)
    }

    @Test
    fun setProduct() {
        val newProduct = product
        sale.product = newProduct
        Assertions.assertEquals(newProduct, sale.product)
    }

    @Test
    fun getQuantity() {
        Assertions.assertEquals(quantitySold, sale.quantity)
    }

    @Test
    fun setQuantity() {
        val newQuantity = 20
        sale.quantity = newQuantity
        Assertions.assertEquals(newQuantity, sale.quantity)
    }

    @Test
    fun getPrice() {
        Assertions.assertEquals(priceSold, sale.price)
    }

    @Test
    fun setPrice() {
        val newPrice = 19.99
        sale.price = newPrice
        Assertions.assertEquals(newPrice, sale.price)
    }

    @Test
    fun getDate() {
        Assertions.assertEquals(saleDate, sale.date)
    }

    @Test
    fun setDate() {
        val newDate = Date()
        sale.date = newDate

        Assertions.assertEquals(newDate, sale.date)
    }
}
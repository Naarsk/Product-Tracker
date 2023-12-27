package com.example.product_tracker.ui.product_manager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.product_tracker.R
import com.example.product_tracker.model.Product

class CreateNewProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_product)
        val productName = intent.getStringExtra("name")
        val product = intent.getSerializableExtra("product") as? Product

        supportActionBar?.title = productName
        if (product != null) {
            Glide.with(this).load(product.imageUrl).into(findViewById(R.id.productImageView))
        }

        val priceTextView = findViewById<TextView>(R.id.priceTextView)
        priceTextView.text = product?.price.toString()
        val quantityTextView = findViewById<TextView>(R.id.quantityTextView)
        quantityTextView.text = product?.quantity.toString()
        val colorTextView = findViewById<TextView>(R.id.colorTextView)
        colorTextView.text = product?.color
        val typeTextView = findViewById<TextView>(R.id.typeTextView)
        typeTextView.text = product?.type
    }
}
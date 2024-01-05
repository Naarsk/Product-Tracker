package com.example.product_tracker.ui.home

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.product_tracker.R
import com.example.product_tracker.model.Product

class ProductActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        val productName = intent.getStringExtra("name")
        val product = intent.getSerializableExtra("product") as? Product

        supportActionBar?.title = productName
        if (product != null) {
            val productImageView = findViewById<ImageView>(R.id.productImageView)
            Glide.with(this)
                .load(product.imagePath)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .fitCenter() // Center the image without cropping
                .into(productImageView)
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
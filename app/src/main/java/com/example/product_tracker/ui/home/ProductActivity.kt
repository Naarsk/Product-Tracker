package com.example.product_tracker.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.product_tracker.R
import com.example.product_tracker.database.ProductDatabaseHelper
import com.example.product_tracker.database.SaleDatabaseHelper
import com.example.product_tracker.model.Product
import com.example.product_tracker.model.Sale
import java.util.Date

class ProductActivity : AppCompatActivity() {
    private lateinit var product: Product

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        val productName = intent.getStringExtra("name")
        product = intent.getSerializableExtra("product") as Product

        supportActionBar?.title = productName
        // Load product image
        val productImageView = findViewById<ImageView>(R.id.productImageView)
        Glide.with(this)
            .load(product.imagePath)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .fitCenter()
            .into(productImageView)
        val priceTextView = findViewById<TextView>(R.id.priceTextView)
        priceTextView.text = product.price.toString()
        val quantityTextView = findViewById<TextView>(R.id.quantityTextView)
        quantityTextView.text = product.quantity.toString()
        val colorTextView = findViewById<TextView>(R.id.colorTextView)
        colorTextView.text = product.color
        val typeTextView = findViewById<TextView>(R.id.typeTextView)
        typeTextView.text = product.type

        val sellButton = findViewById<Button>(R.id.button_sell)
        sellButton.setOnClickListener {
            sellProduct()
        }
        val addButton = findViewById<Button>(R.id.button_add)
        addButton.setOnClickListener {
            addProduct()
        }
    }

    private fun sellProduct() {
        val inputSell = findViewById<EditText>(R.id.input_sell)
        val sellQuantity = inputSell.text.toString().toIntOrNull() ?: 1

        if (sellQuantity > product.quantity) {
            Toast.makeText(this, R.string.toast_not_enough_quantity, Toast.LENGTH_SHORT).show()
            return
        }

        // Define Sale object
        val sale = Sale(
            product = product,
            quantity = sellQuantity,
            price = product.price,
            date = Date()
        )

        val saleDbHelper = SaleDatabaseHelper(this)
        val saleId = saleDbHelper.addSaleToDatabase(sale)

        if (saleId != -1L) {
            // Update product quantity in the database
            val productDbHelper = ProductDatabaseHelper(this)
            val updatedQuantity = product.quantity - sellQuantity
            val success = productDbHelper.updateProductQuantity(product, updatedQuantity)
            Log.i("SellProduct", "Sale added to the sale database")

            if (success) {
                // Display success message
                Toast.makeText(this, R.string.toast_product_sold_success, Toast.LENGTH_SHORT).show()
                Log.i("SellProduct", "Updated product quantity in the product database")

                // Finish the activity
                val intent = Intent()
                intent.putExtra(EXTRA_PRODUCT_UPDATED, true)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                // Display failure message
                Toast.makeText(this, R.string.toast_failed_update_quantity, Toast.LENGTH_SHORT).show()
                Log.e("SellProduct", "Failed to update product quantity in the database")
            }
        } else {
            // Display failure message
            Toast.makeText(this, R.string.toast_failed_record_sale, Toast.LENGTH_SHORT).show()
            Log.e("SellProduct", "Failed to add sale to the database")
        }
    }

    private fun addProduct() {
        val inputAdd = findViewById<EditText>(R.id.input_add)
        val addQuantity = inputAdd.text.toString().toIntOrNull() ?: 0

        // Update product quantity in the database
        val dbHelper = ProductDatabaseHelper(this)
        val updatedQuantity = product.quantity + addQuantity
        val success = dbHelper.updateProductQuantity(product, updatedQuantity)

        if (success && addQuantity > 0) {
            // Display success message
            Toast.makeText(this, R.string.toast_product_added_success, Toast.LENGTH_SHORT).show()
            // Finish the activity
            val intent = Intent()
            intent.putExtra(EXTRA_PRODUCT_UPDATED, true)
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            // Display failure message
            Toast.makeText(this, R.string.toast_failed_update_quantity, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val EXTRA_PRODUCT_UPDATED = "extra_product_updated"
    }
}
package com.example.product_tracker.ui.home

import Product
import android.app.Activity
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.product_tracker.R
import com.example.product_tracker.data.ProductViewModel
import kotlinx.coroutines.launch
import java.util.Date

class ProductActivity : AppCompatActivity() {
    private lateinit var product: Product
    private val productViewModel: ProductViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        product = intent.getSerializableExtra("product") as Product
        supportActionBar?.title = product.name

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

        // Observe LiveData from ViewModel (if needed)
        productViewModel.productUpdateResult.observe(this) { success ->
            if (success) {
                // Handle successful update (e.g., show Toast, finish activity)
                Toast.makeText(this, R.string.toast_product_updated, Toast.LENGTH_SHORT).show()
                val intent = Intent()
                intent.putExtra(EXTRA_PRODUCT_UPDATED, true)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                // Handle update failure (e.g., show error message)
                Toast.makeText(this, R.string.toast_failed_update, Toast.LENGTH_SHORT).show()
            }
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

        lifecycleScope.launch {
            val success = productViewModel.sellProduct(product, sale, sellQuantity)
            if (!success) {
                // Handle failure (e.g., show error message)
                Toast.makeText(this@ProductActivity, R.string.toast_failed_record_sale, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addProduct() {
        val inputAdd = findViewById<EditText>(R.id.input_add)
        val addQuantity = inputAdd.text.toString().toIntOrNull() ?: 0

        lifecycleScope.launch {
            val success = productViewModel.addQuantity(product, addQuantity)if (!success) {
            // Handle failure (e.g., show error message)
            Toast.makeText(this@ProductActivity, R.string.toast_failed_update_quantity, Toast.LENGTH_SHORT).show()
        }
        }
    }

    companion object {
        const val EXTRA_PRODUCT_UPDATED = "extra_product_updated"
    }
}
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
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.product_tracker.MainApplication
import com.example.product_tracker.R
import com.example.product_tracker.data.ProductViewModel
import com.example.product_tracker.data.SaleViewModel
import com.example.product_tracker.model.Product
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date


class ProductActivity : AppCompatActivity() {
    private lateinit var product: Product // Declare product outside the observer

    private lateinit var productImageView : ImageView
    private lateinit var priceTextView : TextView
    private lateinit var quantityTextView : TextView
    private lateinit var colorTextView : TextView
    private lateinit var typeTextView : TextView
    private lateinit var sellButton : Button
    private lateinit var addButton : Button

    private var productDao = MainApplication.productDao
    private var saleDao = MainApplication.saleDao
    private var productViewModel = ProductViewModel(productDao = productDao)
    private val saleViewModel = SaleViewModel(saleDao = saleDao)

    //private lateinit var product : Product

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val productId = intent.getIntExtra("productId", -1)
        Log.d("ProductActivity", "selected product ID: $productId")

        productImageView = findViewById(R.id.productImageView)
        priceTextView = findViewById(R.id.priceTextView)
        quantityTextView = findViewById(R.id.quantityTextView)
        colorTextView = findViewById(R.id.colorTextView)
        typeTextView = findViewById(R.id.typeTextView)
        sellButton = findViewById(R.id.button_sell)
        addButton = findViewById(R.id.button_add)

        // Observe LiveData and update UI
        productViewModel.product.observe(this) { productFromLiveData ->
            Log.d("ProductActivity", "observed product: ${productFromLiveData.code}")
            product = productFromLiveData
        }

        supportActionBar?.title = product.code

        // Load product image
        Glide.with(this)
            .load(product.imagePath)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .fitCenter()
            .into(productImageView)

        priceTextView.text = product.price.toString()
        quantityTextView.text = product.quantity.toString()
        colorTextView.text = product.color
        typeTextView.text = product.type

        sellButton.setOnClickListener {
            sellProduct()
        }

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


        lifecycleScope.launch {
            saleViewModel.createNewSale(productId = product.id, date = Date.from(Instant.now()), price = product.price, quantity = sellQuantity)
            productViewModel.addQuantity(productId= product.id, -sellQuantity)

            // Observe results
            saleViewModel.saleCreationResult.observe(this@ProductActivity) { saleCreationSuccess ->
                productViewModel.productUpdateResult.observe(this@ProductActivity) { productUpdateSuccess ->
                    if (!productUpdateSuccess || !saleCreationSuccess) {
                        Toast.makeText(this@ProductActivity, R.string.toast_failed_record_sale, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun addProduct() {
        val inputAdd = findViewById<EditText>(R.id.input_add)
        val addQuantity= inputAdd.text.toString().toIntOrNull() ?: 0

        lifecycleScope.launch {
            productViewModel.addQuantity(productId = product.id, addQuantity = addQuantity)

            // Observe the result
            productViewModel.productUpdateResult.observe(this@ProductActivity) { success ->
                if (!success) {
                    //If it failed, raise a toast
                    Toast.makeText(
                        this@ProductActivity,
                        R.string.toast_failed_update_quantity,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    companion object {
        const val EXTRA_PRODUCT_UPDATED = "extra_product_updated"
    }
}
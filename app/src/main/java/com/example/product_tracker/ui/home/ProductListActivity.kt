package com.example.product_tracker.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.product_tracker.MainApplication
import com.example.product_tracker.R
import com.example.product_tracker.model.Product

class ProductListActivity : AppCompatActivity() {
    private var productRecycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private lateinit var productList: List<Product>
    private val productDao = MainApplication.productDao

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        val productType = intent.getStringExtra("productType")

        productRecycler = findViewById(R.id.productRecycler)
        progressBar = findViewById(R.id.progressBar)

        productRecycler?.layoutManager = GridLayoutManager(this, 3)
        productRecycler?.setHasFixedSize(true)

        productList = productDao.getProductByType(productType)
        if (productList.isEmpty()) {
            progressBar?.visibility = View.VISIBLE
            Log.d("ProductGallery", "Product list is empty") // Log empty state
            // Handle empty case
        } else {
            Log.d("ProductGallery", "Calling ProductAdapter") // Log before setting adapter
            productRecycler?.adapter = ProductAdapter(productList, this)
            progressBar?.visibility = View.GONE
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PRODUCT && resultCode == Activity.RESULT_OK) {
            val productUpdated = data?.getBooleanExtra(EXTRA_PRODUCT_UPDATED, false) ?: false
            if (productUpdated) {
                // Refresh the UI here
                recreate()
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_PRODUCT = 1
        private const val EXTRA_PRODUCT_UPDATED = "extra_product_updated"
    }
}

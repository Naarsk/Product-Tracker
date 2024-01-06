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
import com.example.product_tracker.R
import com.example.product_tracker.database.ProductDatabaseHelper
import com.example.product_tracker.model.Product

class ProductListActivity : AppCompatActivity() {
    private var productRecycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var products: ArrayList<Product>? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        val productType = intent.getStringExtra("productType")

        productRecycler = findViewById(R.id.productRecycler)
        progressBar = findViewById(R.id.progressBar)

        productRecycler?.layoutManager = GridLayoutManager(this, 3)
        productRecycler?.setHasFixedSize(true)

        products = ArrayList()
        if (products!!.isEmpty()) {
            progressBar?.visibility = View.VISIBLE
            Log.d("ProductGallery", "Calling getProducts")
            products = productType?.let { getProducts(it) }
            Log.d("ProductGallery", "Calling com.example.product_tracker.ui.home.ProductAdapter")
            productRecycler?.adapter = ProductAdapter(products!!,this)
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
    private fun getProducts(productType: String): ArrayList<Product> {
        val dbHelper = ProductDatabaseHelper(this)
        return dbHelper.getProductsByType(productType)
    }

    companion object {
        private const val REQUEST_CODE_PRODUCT = 1
        private const val EXTRA_PRODUCT_UPDATED = "extra_product_updated"
    }
}

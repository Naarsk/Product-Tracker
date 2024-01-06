package com.example.product_tracker.ui.home

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

    private fun getProducts(productType: String): ArrayList<Product> {
        val dbHelper = ProductDatabaseHelper(this)
        return dbHelper.getProductsByType(productType)
    }
}

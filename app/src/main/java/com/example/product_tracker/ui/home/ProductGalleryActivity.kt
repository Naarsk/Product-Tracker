package com.example.product_tracker.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.product_tracker.R
import com.example.product_tracker.database.DatabaseHelper
import com.example.product_tracker.model.Product

class ProductGalleryActivity : AppCompatActivity() {
    private var productRecycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var products: ArrayList<Product>? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val productType = intent.getStringExtra("productType")

        productRecycler = findViewById(R.id.productRecycler)
        progressBar = findViewById(R.id.progressBar)

        productRecycler?.layoutManager = GridLayoutManager(this, 3)
        productRecycler?.setHasFixedSize(true)


        // Check if the permission is granted
        Log.d("onCreate", "Call onCreate")
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                101
            )
        }


        products = ArrayList()
        if (products!!.isEmpty()) {
            progressBar?.visibility = View.VISIBLE
            Log.d("onCreate", "Call getProducts")
            products = productType?.let { getProducts(it) }
            productRecycler?.adapter = ProductAdapter(products!!,this)
            progressBar?.visibility = View.GONE
        }
    }


    private fun getProducts(productType: String): ArrayList<Product> {
        val dbHelper = DatabaseHelper(this)
        return dbHelper.getProductsByType(productType)
        // return ProductDataSource.getProductList(productType)
    }
}

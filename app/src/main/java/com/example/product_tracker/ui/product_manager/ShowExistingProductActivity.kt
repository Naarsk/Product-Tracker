package com.example.product_tracker.ui.product_manager

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
import com.example.product_tracker.MainApplication
import com.example.product_tracker.R
import com.example.product_tracker.data.ProductDao
import com.example.product_tracker.model.Product

class ShowExistingProductActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_existing_product)
        val productDao: ProductDao = MainApplication.productDao

        val productRecycler: RecyclerView? = findViewById(R.id.deleteProductRecycler)
        val progressBar: ProgressBar? = findViewById(R.id.deleteProgressBar)

        productRecycler?.layoutManager = GridLayoutManager(this, 3)
        productRecycler?.setHasFixedSize(true)


        // Check if the permission is granted
        Log.d("onCreate", "Call onCreate")
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            Log.d("onCreate", "Asking for permission")
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_MEDIA_IMAGES),101)
        }
        else{Log.d("onCreate", "Permission READ_MEDIA_IMAGES is granted")}


        val products: List<Product> = productDao.getAllProduct()
        if (products.isEmpty()) {
            progressBar?.visibility = View.VISIBLE
            Log.d("onCreate", "Call getProducts")
            productRecycler?.adapter = EditDeleteAdapter(this)
            progressBar?.visibility = View.GONE
        }
    }
}

package com.example.product_tracker.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.product_tracker.MainApplication
import com.example.product_tracker.R
import com.example.product_tracker.data.ProductListViewModel

class ProductListActivity : AppCompatActivity() {
    private lateinit var productRecycler: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var noProductsTextView: TextView

    private var productDao = MainApplication.productDao
    private var viewModel: ProductListViewModel  = ProductListViewModel(productDao = productDao)


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        val productType = intent.getStringExtra("productType")
        Log.d("ProductListActivity", "selected product type: $productType")

        productRecycler = findViewById(R.id.productRecycler)
        progressBar = findViewById(R.id.progressBar)
        noProductsTextView = findViewById(R.id.noProductsTextView)

        productRecycler.layoutManager = GridLayoutManager(this, 3)
        productRecycler.setHasFixedSize(true)

        // Observe LiveData and update UI
        viewModel.products.observe(this) { productList ->
            Log.d("ProductListActivity", "Observed product list: $productList")
            if (productList.isEmpty()) {
                progressBar.visibility = View.VISIBLE
                Log.d("ProductListActivity", "Product list is empty")
                // Display "No products found" message
                progressBar.visibility = View.GONE
                noProductsTextView.visibility = View.VISIBLE
            } else {
                noProductsTextView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                Log.d("ProductListActivity", "Calling ProductAdapter")
                productRecycler.adapter = ProductAdapter(productList, this)
                progressBar.visibility = View.GONE
            }
        }

        // Fetch products
        productType?.let { viewModel.getProducts(it) }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PRODUCT && resultCode == RESULT_OK) {
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

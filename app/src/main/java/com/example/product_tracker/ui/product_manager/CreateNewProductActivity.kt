package com.example.product_tracker.ui.product_manager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.product_tracker.R
import com.example.product_tracker.database.DatabaseHelper
import com.example.product_tracker.model.Product

class CreateNewProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_product)

        val createNewProductButton: Button = findViewById(R.id.createNewProductButton)
        createNewProductButton.setOnClickListener {
            // Retrieve the values from the EditText fields
            val loadPriceTextView: EditText = findViewById(R.id.loadPriceTextView)
            val loadTypeTextView: EditText = findViewById(R.id.loadTypeTextView)
            val loadColorTextView: EditText = findViewById(R.id.loadColorTextView)
            val loadQuantityTextView: EditText = findViewById(R.id.loadQuantityTextView)
            val loadIdTextView: EditText = findViewById(R.id.loadIdTextView)

            val price: String = loadPriceTextView.text.toString()
            val type: String = loadTypeTextView.text.toString()
            val color: String = loadColorTextView.text.toString()
            val quantity: String = loadQuantityTextView.text.toString()
            val id: String = loadIdTextView.text.toString()

            // Create a Product instance with the values
            val product = Product(id, type, "https://example.com/mock-image-url", price.toDouble(), quantity.toInt(), color)

            // Insert the values into the database
            val dbHelper = DatabaseHelper(this)
            val newRowId = dbHelper.addProductToDatabase(product)
            if (newRowId != -1L){
                // Display success message
                Toast.makeText(this, "Product has been created successfully", Toast.LENGTH_SHORT).show()
                // Return to ProductManagerFragment
                finish()
            }
        }
    }
}
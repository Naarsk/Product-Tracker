
import android.os.Build
import android.os.Bundle
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
import com.example.product_tracker.model.Product

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
    }

    private fun sellProduct() {
        val inputSell = findViewById<EditText>(R.id.input_sell)
        val sellQuantity = inputSell.text.toString().toIntOrNull() ?: 1

        if (sellQuantity > product.quantity) {
            Toast.makeText(this, "Not enough quantity available", Toast.LENGTH_SHORT).show()
            return
        }

        // Update product quantity in the database
        val dbHelper = ProductDatabaseHelper(this)
        val updatedQuantity = product.quantity - sellQuantity
        val success = dbHelper.updateProductQuantity(product, updatedQuantity)

        if (success) {
            // Display success message
            Toast.makeText(this, "Product sold successfully!", Toast.LENGTH_SHORT).show()
            // Finish the activity
            finish()
        } else {
            // Display failure message
            Toast.makeText(this, "Failed to update product quantity", Toast.LENGTH_SHORT).show()
        }
    }
}
package com.example.product_tracker.ui.product_manager

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.product_tracker.R
import com.example.product_tracker.Utils
import com.example.product_tracker.database.DatabaseHelper
import com.example.product_tracker.model.Product
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateNewProductActivity : AppCompatActivity() {
    // Declare the activity result launcher
    private lateinit var galleryLauncher: ActivityResultLauncher<String>

    private lateinit var productLoadImageButton: ImageButton
    private lateinit var copiedImagePath: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_product)

        // Initialize the activity result launchers
        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent(), galleryResultCallback)

        productLoadImageButton = findViewById(R.id.productLoadImageButton)
        productLoadImageButton.setOnClickListener{showPictureSelectionPopup()}

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
            val product = Product(id, type, copiedImagePath, price.toDouble(), quantity.toInt(), color)

            // Insert the values into the database
            val dbHelper = DatabaseHelper(this)
            val newRowId = dbHelper.addProductToDatabase(product)
            if (newRowId != -1L){
                // Display success message
                Toast.makeText(this, "Product has been created successfully", Toast.LENGTH_SHORT).show()
                // Return to ProductManagerFragment
                finish()
            } else {
                Toast.makeText(this, "Product creation has failed", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
    }
    private fun showPictureSelectionPopup() {
        val options = arrayOf<CharSequence>("Select from Gallery", "Take Picture")
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("Select Picture")
        builder.setItems(options) { _, item ->
            when (item) {
                0 -> {
                    Log.d("Select Picture", "Launch gallery")
                    galleryLauncher.launch("image/*")
                }
                1 -> {
                    // cameraLauncher
                }
            }
        }
        builder.show()
    }

    private val galleryResultCallback = ActivityResultCallback { uri: Uri? ->
        if (uri != null) {
            val originalImagePath = getFilePathFromContentUri(uri)

            // Create a hidden folder to store the copied image
            val hiddenFolderPath = getExternalFilesDir(null)?.absolutePath + "/hidden_folder/" // .hidden_folder

            // Generate a unique file name for the copied image
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val newFileName = "product_image_$timeStamp.jpg"

            // Create the file path for the copied image
            copiedImagePath =
                originalImagePath?.let { Utils().copyFile(it, hiddenFolderPath, newFileName) }.toString()

            Log.d("CreateNewProduct", "Image copied successfully to: $copiedImagePath")

            // Load the image into the productLoadImageButton
            Glide.with(this)
                .load(copiedImagePath)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .fitCenter() // Center the image without cropping
                .into(productLoadImageButton)
        }
    }

    private fun getFilePathFromContentUri(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)

        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return it.getString(columnIndex)
            }
        }
        return null
    }

}

/*
private fun getAllPictures(): ArrayList<Image> {
    val pictures = ArrayList<Image>()
    val picturesDirectory = File(Environment.getExternalStorageDirectory(), "Pictures")

    if (picturesDirectory.exists() && picturesDirectory.isDirectory) {
        val imageFiles = picturesDirectory.listFiles()

        imageFiles?.let {
            for (file in it) {
                val imageName = file.name
                val imagePath = file.absolutePath
                val image = Image(imageName = imageName, imagePath = imagePath)
                pictures.add(image)
            }
        }
    }*/

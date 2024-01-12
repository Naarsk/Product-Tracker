package com.example.product_tracker.ui.product_manager

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.product_tracker.R
import com.example.product_tracker.Utils
import com.example.product_tracker.database.ProductDatabaseHelper
import com.example.product_tracker.model.Product
import com.example.product_tracker.model.ProductTypeMapper
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateNewProductActivity : AppCompatActivity() {

    // Declare the activity result launchers
    private lateinit var galleryLauncher: ActivityResultLauncher<String>
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>

    private lateinit var productLoadImageButton: ImageButton
    private lateinit var selectedImagePath: String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_product)

        // Declare components
        val loadPriceTextView: EditText = findViewById(R.id.loadPriceTextView)
        val loadTypeSpinner: Spinner = findViewById(R.id.loadTypeSpinner)
        val loadColorTextView: EditText = findViewById(R.id.loadColorTextView)
        val loadQuantityTextView: EditText = findViewById(R.id.loadQuantityTextView)
        val loadIdTextView: EditText = findViewById(R.id.loadIdTextView)
        val createNewProductButton: Button = findViewById(R.id.createNewProductButton)

        // Create an ArrayAdapter to populate the spinner with the values:
        val typeValues = ProductTypeMapper(this).getAllTypeValues()
        val translatedTypeValues = ProductTypeMapper(this).getAllTranslatedTypeValues()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, translatedTypeValues)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        loadTypeSpinner.adapter = adapter

        // Initialize the activity result launchers for the productLoadImageButton
        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent(), galleryResultCallback)
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {}

        productLoadImageButton = findViewById(R.id.productLoadImageButton)
        productLoadImageButton.setOnClickListener {
            showPictureSelectionPopup()
        }

        // Create the product when createNewProductButton is clicked
        createNewProductButton.setOnClickListener {
            // Retrieve the values from the EditText fields
            val price: String = loadPriceTextView.text.toString()
            val type: String = typeValues[loadTypeSpinner.selectedItemPosition]
            val color: String = loadColorTextView.text.toString()
            val quantity: String = loadQuantityTextView.text.toString()
            val id: String = loadIdTextView.text.toString()

            // Create a Product instance with the values
            val product = Product(id, type, selectedImagePath, price.toDouble(), quantity.toInt(), color)
            // Insert the values into the database
            val dbHelper = ProductDatabaseHelper(this)
            val newRowId = dbHelper.addProductToDatabase(product)
            if (newRowId != -1L) {
                // Display success message
                Toast.makeText(this, getString(R.string.product_created_success), Toast.LENGTH_SHORT).show()
                // Return to ProductManagerFragment
                finish()
            } else {
                // Display failure message
                Toast.makeText(this, getString(R.string.product_creation_failed), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    // USED TO POPULATE THE IMAGE FIELD

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
                    Log.d("Select Picture", "Launch camera")
                    launchCamera()
                }
            }
        }
        builder.show()
    }

    // SELECT FROM GALLERY

    private val galleryResultCallback = ActivityResultCallback { uri: Uri? ->
        if (uri != null) {
            val originalImagePath = getFilePathFromContentUri(uri)

            // Create a hidden folder to store the copied image
            val hiddenFolderPath = getExternalFilesDir(null)?.absolutePath + "/hidden_folder/"

            // Generate a unique file name for the copied image
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val newFileName = "product_image_$timeStamp.jpg"

            // Create the file path for the copied image
            selectedImagePath =
                originalImagePath?.let { Utils().copyFile(it, hiddenFolderPath, newFileName) }.toString()
            Log.d("SelectFromGallery", "Image copied successfully to: $selectedImagePath")

            // Load the image into the productLoadImageButton
            Glide.with(this)
                .load(selectedImagePath)
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


    // SELECT FROM CAMERA
    private fun launchCamera() {
        checkCameraPermission()

        val hiddenFolderPath = getExternalFilesDir(null)?.absolutePath + "/hidden_folder/"
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val newFileName = "product_image_$timeStamp.jpg"
        val newFile = File(hiddenFolderPath, newFileName)
        selectedImagePath = newFile.absolutePath

        val photoUri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", newFile)
        cameraLauncher.launch(photoUri)
        Glide.with(this)
            .load(selectedImagePath)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .fitCenter() // Center the image without cropping
            .into(productLoadImageButton)
    }
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Log.d("checkCameraPermission", "Permission already granted")
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
            Log.d("checkCameraPermission", "Asking for permission")
            Manifest.permission.CAMERA
        }
    }
    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }
}
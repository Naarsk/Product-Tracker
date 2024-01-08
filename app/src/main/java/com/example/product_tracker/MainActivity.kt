package com.example.product_tracker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.product_tracker.database.ProductDatabaseHelper
import com.example.product_tracker.database.SaleDatabaseHelper
import com.example.product_tracker.databinding.ActivityMainBinding
import com.example.product_tracker.ui.menu.LanguageChangeHandler


class MainActivity : AppCompatActivity() {
    private var mAppBarConfiguration: AppBarConfiguration? = null
    private lateinit var languageChangeHandler: LanguageChangeHandler
    private lateinit var toolbar: Toolbar
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d("MainActivity", "Call onCreate")

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawer = binding.drawerLayout
        val navigationView = binding.navView

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_home, R.id.nav_manage_product).setOpenableLayout(drawer).build()

        val navController = findNavController(this, R.id.nav_host_fragment_content_main)
        setupActionBarWithNavController(this, navController, mAppBarConfiguration!!)
        setupWithNavController(navigationView, navController)

        // Check for permissions
        checkPermission(
            Manifest.permission.READ_MEDIA_IMAGES, READ_IMAGES_PERMISSION_CODE)

        // Initialize the databases
        initializeDatabases()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        // Initialize the language change handler
        languageChangeHandler = LanguageChangeHandler(this)

        // Assuming you have a reference to the menu item in your activity or fragment
        val languageMenuItem: MenuItem = menu.findItem(R.id.action_language)

        // Set a click listener for the language menu item
        languageMenuItem.setOnMenuItemClickListener { item ->
            val languageChangeHandler = LanguageChangeHandler(this)
            languageChangeHandler.handleLanguageChange(item, toolbar)
            true
        }
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        languageChangeHandler.handleLanguageChange(item, toolbar)
        return super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(this, R.id.nav_host_fragment_content_main)
        return (navigateUp(navController, mAppBarConfiguration!!)
                || super.onSupportNavigateUp())
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == READ_IMAGES_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to check and request permissions
    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@MainActivity, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            Log.d("MainActivity", "Requesting permission")
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
        } else {
            Log.d("MainActivity", "Permission already granted")
        }
    }
    private fun initializeDatabases() {
        fun checkDatabaseExists(dbPath: String): Boolean {
            val dbFile = getDatabasePath(dbPath)
            return dbFile.exists()
        }
        // Initialize the database helpers
        val productDbHelper = ProductDatabaseHelper(this)
        val saleDbHelper = SaleDatabaseHelper(this)

        // Check if the product database is already present and create it if it doesn't exist
        val productDbPath = getDatabasePath(ProductDatabaseHelper.DATABASE_NAME).absolutePath
        val productDbExists = checkDatabaseExists(productDbPath)
        if (!productDbExists) {
            productDbHelper.writableDatabase
            Log.d("MainActivity", "Product database created at path: $productDbPath")
        } else {
            Log.d("MainActivity", "Product database already present at path: $productDbPath")
        }

        // Check if the sale database is already present and create it if it doesn't exist
        val saleDbPath = getDatabasePath(SaleDatabaseHelper.DATABASE_NAME).absolutePath
        val saleDbExists = checkDatabaseExists(saleDbPath)
        if (!saleDbExists) {
            saleDbHelper.writableDatabase
            Log.d("MainActivity", "Sale database created at path: $saleDbPath")
        } else {
            Log.d("MainActivity", "Sale database already present at path: $saleDbPath")
        }
    }
    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
        private const val READ_IMAGES_PERMISSION_CODE = 101
    }
}

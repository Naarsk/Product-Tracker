package com.example.product_tracker

import android.Manifest
import android.content.Context
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
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.room.Room
import com.example.product_tracker.data.AppDatabase
import com.example.product_tracker.data.ProductDao
import com.example.product_tracker.data.SaleDao
import com.example.product_tracker.databinding.ActivityMainBinding
import com.example.product_tracker.ui.menu.LanguageChangeHandler


class MainActivity : AppCompatActivity() {
    private var mAppBarConfiguration: AppBarConfiguration? = null
    private lateinit var languageChangeHandler: LanguageChangeHandler
    private lateinit var toolbar: Toolbar
    private lateinit var productDao: ProductDao
    private lateinit var saleDao: SaleDao

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d("MainActivity", "Call onCreate")

        // Retrieve the stored language preference
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val selectedLanguage = sharedPreferences.getString(LanguageChangeHandler.PREF_KEY_LANGUAGE, "default_language")

        // Apply the selected language to the app's configuration
        if (selectedLanguage != null) {
            Log.d("MainActivity", "Found language: $selectedLanguage")
            LanguageChangeHandler.LanguageManager.setAppLanguage(this, selectedLanguage)
        }else{
            Log.e("MainActivity", "No saved language")
        }

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

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(this, navController, mAppBarConfiguration!!)
        setupWithNavController(navigationView, navController)

        // Check for permissions
        checkPermission(
            Manifest.permission.READ_MEDIA_IMAGES, READ_IMAGES_PERMISSION_CODE)
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
        val navController = findNavController(this, R.id.nav_host_fragment)
        return (navigateUp(navController, mAppBarConfiguration!!)
                || super.onSupportNavigateUp())
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
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

    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
        private const val READ_IMAGES_PERMISSION_CODE = 101
    }
}

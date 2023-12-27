package com.example.product_tracker

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.product_tracker.database.DatabaseHelper
import com.example.product_tracker.databinding.ActivityMainBinding
import com.example.product_tracker.example.ProductDataSource
import com.example.product_tracker.model.Product
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private var mAppBarConfiguration: AppBarConfiguration? = null
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawer = binding.drawerLayout
        val navigationView = binding.navView
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_home, R.id.nav_manage_product).setOpenableLayout(drawer).build()
        val navController = findNavController(this, R.id.nav_host_fragment_content_main)
        setupActionBarWithNavController(this, navController, mAppBarConfiguration!!)
        setupWithNavController(navigationView, navController)

        // Initialize the database helper
        dbHelper = DatabaseHelper(this)

        // Check if the database is already present
        var dbPath = getDatabasePath(DatabaseHelper.DATABASE_NAME).absolutePath
        var dbExists = checkDatabaseExists(dbPath)

        // Create the database if it doesn't exist
        if (!dbExists) {
            createDatabase()
            Log.d("MainActivity", "Database created at path: $dbPath")
            dbPath = getDatabasePath(DatabaseHelper.DATABASE_NAME).absolutePath
            dbExists = checkDatabaseExists(dbPath)
        }

        // Populate the database with data from ProductDataSource if data is missing
        val productList = ProductDataSource.getProductList(null)
        val productIds = productList.map { it.id }
        if (dbExists) {
            val missingProductIds = dbHelper.getMissingProductIds(productIds)
            if (missingProductIds.isNotEmpty()) {
                val missingProducts = productList.filter { it.id in missingProductIds }
                val addedEntries = addProductsToDatabase(missingProducts as ArrayList<Product>)
                Log.d("MainActivity", "Added $addedEntries entries to the database")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(this, R.id.nav_host_fragment_content_main)
        return (navigateUp(navController, mAppBarConfiguration!!)
                || super.onSupportNavigateUp())
    }

    private fun checkDatabaseExists(dbPath: String): Boolean {
        val dbFile = getDatabasePath(dbPath)
        return dbFile.exists()
    }

    private fun createDatabase() {
        dbHelper.writableDatabase
    }

    private fun addProductsToDatabase(productList: ArrayList<Product>): Int {
        return dbHelper.addProductsToDatabase(productList)
    }
}
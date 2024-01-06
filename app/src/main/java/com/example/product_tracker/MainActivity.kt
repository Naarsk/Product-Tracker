package com.example.product_tracker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.product_tracker.database.DatabaseHelper
import com.example.product_tracker.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private var mAppBarConfiguration: AppBarConfiguration? = null
    private lateinit var dbHelper: DatabaseHelper

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d("MainActivity", "Call onCreate")

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

        // Check for permissions
        checkAskPermission()

        // Initialize the database helper
        dbHelper = DatabaseHelper(this)

        // Check if the database is already present and create the database if it doesn't exist
        val dbPath = getDatabasePath(DatabaseHelper.DATABASE_NAME).absolutePath
        val dbExists = checkDatabaseExists(dbPath)
        if (!dbExists) {
            createDatabase()
            Log.d("MainActivity", "Database created at path: $dbPath")
        } else {
            Log.d("MainActivity", "Database already present at path: $dbPath")
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun askPermission() {
        Manifest.permission.READ_MEDIA_IMAGES
        val permission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.d("MainActivity", "Permission READ_MEDIA_IMAGES denied")
            finish()
        } else {
            Log.d("MainActivity", "Permission READ_MEDIA_IMAGES granted")
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkAskPermission(){
    val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
    if(permission == PackageManager.PERMISSION_GRANTED){
        Log.d("MainActivity", "Permission READ_MEDIA_IMAGES already granted")
    }
    else{
        Log.d("MainActivity", "Asking for permission READ_EXTERNAL_STORAGE")
        askPermission()}
    }
}

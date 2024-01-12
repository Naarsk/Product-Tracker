package com.example.product_tracker.ui.menu
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import com.example.product_tracker.R
import java.util.Locale

class LanguageChangeHandler(private val context: Context) {

    companion object {
        private const val TAG = "LanguageChangeHandler"
        const val PREF_KEY_LANGUAGE = "language"
    }

    object LanguageManager {
        fun setAppLanguage(context: Context, languageCode: String) {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)

            val config = Configuration(context.resources.configuration)
            config.setLocale(locale)

            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }
    }

    fun handleLanguageChange(item: MenuItem, anchorView: View) {
        when (item.itemId) {
            R.id.action_language -> {
                Log.d(TAG, "handleLanguageChange: Anchor View: $anchorView")
                // Create a language selection menu
                val languageMenu = PopupMenu(context, anchorView, Gravity.END)
                languageMenu.menuInflater.inflate(R.menu.language_menu, languageMenu.menu)
                // Set a click listener for each language menu item
                languageMenu.setOnMenuItemClickListener { languageItem ->
                    when (languageItem.itemId) {
                        R.id.language_en -> {
                            changeLanguage("en")
                            true
                        }
                        R.id.language_it -> {
                            changeLanguage("it")
                            true
                        }
                        R.id.language_zh -> {
                            changeLanguage("zh")
                            true
                        }
                        else -> false
                    }
                }

                // Show the language selection menu
                languageMenu.show()
            }
        }
    }

    private fun changeLanguage(languageCode: String) {
        LanguageManager.setAppLanguage(context, languageCode)

        // Store the selected language preference
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(PREF_KEY_LANGUAGE, languageCode).apply()

        // Restart the app to apply the language change
        restartApp()
    }

    private fun restartApp() {
        val packageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context.startActivity(intent)
    }
}



package com.example.product_tracker.model

import android.content.Context
import com.example.product_tracker.R

class ProductTypeMapper(private val context: Context) {
    private val typeValues = arrayOf("bag", "wallet", "gloves/scarf", "suitcase", "belt", "accessory")

    fun getAllTypeValues(): Array<String> {
        return typeValues
    }

    fun getAllTranslatedTypeValues(): Array<String> {
        return context.resources.getStringArray(R.array.allowed_types)
    }

}
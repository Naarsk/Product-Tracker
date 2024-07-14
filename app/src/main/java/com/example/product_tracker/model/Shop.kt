package com.example.product_tracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Shop (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Internal primary key
    val name: String,
    val location: String
)
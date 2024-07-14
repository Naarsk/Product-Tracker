package com.example.product_tracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Internal primary key
    val firstName: String,
    val lastName: String,
    val createdAt: Date,
    val updatedAt: Date
)
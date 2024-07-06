package com.example.product_tracker.model

import androidx.room.Entity

@Entity
data class User (
    val id: Int,
    val firstName: String,
    val lastName: String
)
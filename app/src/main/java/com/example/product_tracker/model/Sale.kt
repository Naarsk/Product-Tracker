package com.example.product_tracker.model

import Product
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "sales", foreignKeys = [
    ForeignKey(
        entity = Product::class,
        parentColumns = ["id"], // Referencing the internal primary key of Product
        childColumns = ["product_id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class Sale(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = "product_id", index = true)
    var productId: Int,
    var quantity: Int,
    var price: Double,
    var date: Date,
    var createdAt: Date = Date(),
    var updatedAt: Date = Date()
)
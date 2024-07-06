package com.example.product_tracker.model


import Product
import androidx.room.ForeignKey
import java.util.Date

@Entity(tableName = "sales", foreignKeys = [
    ForeignKey(
        entity = Product::class,
        parentColumns = ["id"], // Referencing the internal primary key of Product
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class Sale(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = "product_id", index = true)
    var productId: String,
    var quantity: Int,
    var price: Double,
    var date: Date,
    @ColumnInfo(name = "created_at")
    var createdAt: Date = Date(),
    @ColumnInfo(name = "updated_at")
    var updatedAt: Date = Date()
)
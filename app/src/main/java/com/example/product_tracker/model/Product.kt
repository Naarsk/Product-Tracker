

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Internal primary key
    var code: String, // Original product ID
    var type: String,
    var imagePath: String,
    var price: Double,
    var quantity: Int,
    var color: String,
    @ColumnInfo(name = "created_at")
    var createdAt: Date = Date(),
    @ColumnInfo(name = "updated_at")
    var updatedAt: Date = Date()
)
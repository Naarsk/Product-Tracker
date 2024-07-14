
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


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
    var createdAt: Date,
    var updatedAt: Date
)
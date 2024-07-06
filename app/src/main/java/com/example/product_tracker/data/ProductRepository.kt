import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject // For dependency injection
import com.example.product_tracker.data.ProductDao // Replace with your actual package name
class ProductRepository @Inject constructor(private val productDao: ProductDao) {

    fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()

    suspend fun insertProduct(product: Product) = withContext(Dispatchers.IO) {
        productDao.insertProduct(product)
    }

    suspend fun getProductById(productId: Int): Product? = withContext(Dispatchers.IO) {
        productDao.getProductById(productId)
    }

    // ... add other functions as needed (update, delete, etc.)
}
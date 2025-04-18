import android.net.Credentials
import android.provider.ContactsContract
import com.example.product_tracker.model.User
import retrofit2.Call
import retrofit2.http.*

interface UserApi {

    @POST("register")
    fun register(@Body user: User): Call<Void>

    @POST("login")
    fun login(@Body credentials: Credentials): Call<User>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Call<User>

    @PUT("users/{username}/email")
    fun updateUserEmail(@Path("username") username: String, @Body email: ContactsContract.CommonDataKinds.Email): Call<Void>

    @DELETE("users/{username}")
    fun deleteUser(@Path("username") username: String): Call<Void>
}
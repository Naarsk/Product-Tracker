package com.example.product_tracker.ui.user_management

import UserApi
import android.net.Credentials
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.product_tracker.data.UserDao
import com.example.product_tracker.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class UserViewModel(private val userDao : UserDao, private val userApi: UserApi) : ViewModel() {

    val userList : LiveData<List<User>> = userDao.getAllUser()

    fun createNewUser(username : String, password : String, email : String){

        viewModelScope.launch(Dispatchers.IO) { // Run on a background thread
            val user = User(
                username = username,
                password = password,
                email = email,
                createdAt = Date.from(Instant.now()),
                updatedAt = Date.from(Instant.now())
            )
            userDao.insertUser(user)
        }
    }
    fun registerUser(user: User) {
        viewModelScope.launch {
            try {
                val response = userApi.register(user).execute()
                if (response.isSuccessful) {
                    // Registration successful
                    // ... handle success (e.g., navigate to login screen) ...
                } else {
                    // Registration failed
                    // ... handle error (e.g., show error message) ...
                }
            } catch (e: Exception) {
                // Handle network or other exceptions
                // ...
            }
        }
    }

    // ... Implement other API calls similarly ...

    fun loginUser(credentials: Credentials) {
        // ...
    }

    fun getUser(username: String) {
        // ...
    }

    fun updateUserEmail(username: String, email: ContactsContract.CommonDataKinds.Email) {
        // ...
    }

    fun deleteUser(id : Int){
        userDao.deleteUser(userId = id)
    }
}
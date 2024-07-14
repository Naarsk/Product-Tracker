package com.example.product_tracker.ui.user_management

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.product_tracker.MainApplication
import com.example.product_tracker.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class UserViewModel : ViewModel() {

    private val userDao = MainApplication.userDatabase.getUserDao()

    val userList : LiveData<List<User>> = userDao.getAllUser()

    fun createNewUser(firstName : String, lastName : String){

        viewModelScope.launch(Dispatchers.IO) { // Run on a background thread
            val user = User(
                firstName = firstName,
                lastName = lastName,
                createdAt = Date.from(Instant.now()),
                updatedAt = Date.from(Instant.now())

            )
            userDao.insertUser(user)
        }
    }

    fun deleteUser(id : Int){
        userDao.deleteUser(id = id)
    }
}
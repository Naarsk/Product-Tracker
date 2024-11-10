package com.example.product_tracker.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.product_tracker.model.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(user :User)

    @Query("DELETE FROM users WHERE id = :userId") // Assuming "users" is your table name
    fun deleteUser(userId: Int)

    @Query("SELECT * FROM users")
    fun getAllUser() : LiveData<List<User>>

}
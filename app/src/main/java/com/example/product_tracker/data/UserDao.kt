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

    @Delete
    fun deleteUser(id : Int)

    @Query("SELECT * FROM USER")
    fun getAllUser() : LiveData<List<User>>

}
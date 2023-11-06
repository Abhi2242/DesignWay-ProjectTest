package com.smartgeek.designwayproject.services

import androidx.room.*
import com.smartgeek.designwayproject.model.userdata.User

@Dao
interface UserDao {
    @get:Query("SELECT * FROM User_DataBase")
    val getAllUser: List<User>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Delete
    fun delete(user: User?)
}
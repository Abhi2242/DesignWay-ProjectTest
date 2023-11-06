package com.smartgeek.designwayproject.model.userdata

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smartgeek.designwayproject.services.UserDao

@Database(entities = [User::class], version = 1)
abstract class UserDB: RoomDatabase() {

    abstract fun userDao(): UserDao
}
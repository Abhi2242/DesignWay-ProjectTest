package com.smartgeek.designwayproject.model.userdata

import androidx.room.*

@Entity(tableName = "User_DataBase")
data class User(
    @PrimaryKey(autoGenerate = true) var uId: Int,
    @ColumnInfo(name = "User_Full_Name") val uFullName: String,
    @ColumnInfo(name = "User_Name") val uName: String,
    @ColumnInfo(name = "User_Password") val uPass: String
)

package com.smartgeek.designwayproject.activities.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.room.Room
import com.smartgeek.designwayproject.R
import com.smartgeek.designwayproject.activities.dogpages.DogBreedActivity
import com.smartgeek.designwayproject.model.userdata.User
import com.smartgeek.designwayproject.model.userdata.UserDB
import com.smartgeek.designwayproject.model.userdata.UserData

class SignupActivity : AppCompatActivity() {

    private lateinit var fullName: EditText
    private lateinit var userName: EditText
    private lateinit var uPass: EditText
    private lateinit var registerBtn: Button
    private var userDataList: ArrayList<UserData>? = null
    private var userLoginData: ArrayList<User> = ArrayList()
    private lateinit var errorText: TextView

    data class DuplicateUserLoginData(
        val fullName: String,
        val userName: String,
        val password: String
    )

    private var duplicateLoginData: ArrayList<DuplicateUserLoginData>? = ArrayList()
    private lateinit var dataBase: UserDB

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        dataBase =
            Room.databaseBuilder(applicationContext, UserDB::class.java, "User_DataBase")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

//        loadUserData(dataBase)
//        loadData()

        fullName = findViewById(R.id.et_sFullName)
        userName = findViewById(R.id.et_sUsername)
        uPass = findViewById(R.id.et_sPass)
        registerBtn = findViewById(R.id.btn_register)
        errorText = findViewById(R.id.tv_error_text)

        registerBtn.setOnClickListener {
            val fullName = fullName.text.toString()
            val userName = userName.text.toString()
            val pass = uPass.text.toString()
            val regex =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%&*()_+=|<>?{}\\\\~-]).{8,}".toRegex()
            if (fullName.isNotEmpty() && userName.isNotEmpty() && pass.isNotEmpty()) {
                if (pass.contains(regex)) {

                    var userStatus = 0
                    userLoginData = ((dataBase.userDao().getAllUser as ArrayList<User>?)!!)
                    duplicateLoginData =
                        userLoginData.map {
                            DuplicateUserLoginData(
                                it.uFullName,
                                it.uName,
                                it.uPass
                            )
                        } as ArrayList<DuplicateUserLoginData>?
                    if (duplicateLoginData?.contains(
                            DuplicateUserLoginData(
                                fullName,
                                userName,
                                pass
                            )
                        ) == false
                    ) {
                        dataBase.userDao().insert(User(0, fullName, userName, pass))
                        userStatus = 1
                    }
                    if (userStatus == 1) {
                        signUp()
                    } else {
                        errorText.visibility = View.VISIBLE
                        errorText.text = "$userLoginData"
                    }

//                    if (duplicateLoginData?.contains(DuplicateUserLoginData(fullName, userName, pass)) == true) {
//                        Thread{
//                            dataBase.userDao().insert(User(0, fullName, userName,pass))
//
//                            Log.i("user data signup db", "${dataBase.userDao().getAllUser}")
//                        }
////                        saveLoginData(dataBase, fullName, userName, pass)
//                    } else {
//                        errorText.visibility = View.VISIBLE
//                        errorText.text = "User Already Exist"
////                        Toast.makeText(this, "User Already exist", Toast.LENGTH_SHORT).show()
//                    }

                    /* SharedPreferences method */
//                    if (userDataList?.contains(UserData(fullName,userName,pass)) == false){
//                        userDataList?.size?.let { userDataList?.add(it, UserData(fullName,userName,pass)) }
//                        Toast.makeText(this, "$userDataList", Toast.LENGTH_SHORT).show()
//                        saveData()
//                        startActivity(/* intent = */ Intent(this, DogBreedActivity::class.java)
//                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//                        )
//                    }
//                    else{
//                        Toast.makeText(this, "User Already exist", Toast.LENGTH_SHORT).show()
//                    }
                } else {
                    errorText.visibility = View.VISIBLE
//                    Toast.makeText(this, "all details not present", Toast.LENGTH_SHORT).show()
                }
            } else {
                errorText.visibility = View.VISIBLE
                errorText.text = "Enter All Details"
//                Toast.makeText(this, pass, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun signUp() {
        startActivity(
            Intent(this, DogBreedActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

//    private fun saveData() {
//        val shareData: SharedPreferences = getSharedPreferences("Shared Data", MODE_PRIVATE)
//        val editor: SharedPreferences.Editor = shareData.edit()
//        val gson = Gson()
//        val json: String = gson.toJson(userDataList?.distinct())
//        editor.putString("Array List", json)
//        editor.apply()
//    }

//    private fun loadData() {
//        val shareData: SharedPreferences = getSharedPreferences("Shared Data", MODE_PRIVATE)
//        val gson = Gson()
//        val json: String? = shareData.getString("Array List", null)
//        val type = object : TypeToken<ArrayList<UserData>>() {}.type
//        userDataList = gson.fromJson(json, type)
//        Log.i("Array List load user data", "$userDataList")
//
//        if (userDataList?.size == null) {
//            userDataList = ArrayList()
//        }
//    }

    private fun loadUserData(db: UserDB) {
        Thread {
            userLoginData = ((db.userDao().getAllUser as ArrayList<User>?)!!)
            Log.i("user data signup1", "$userLoginData")
            duplicateLoginData =
                userLoginData.map {
                    DuplicateUserLoginData(
                        it.uFullName,
                        it.uName,
                        it.uPass
                    )
                } as ArrayList<DuplicateUserLoginData>?
        }
        Log.i("user data signup2", "$duplicateLoginData")
    }
}
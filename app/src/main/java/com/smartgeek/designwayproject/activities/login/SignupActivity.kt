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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    private lateinit var fullName: EditText
    private lateinit var userName: EditText
    private lateinit var uPass: EditText
    private lateinit var registerBtn: Button
    private lateinit var errorText: TextView

    data class DuplicateUserLoginData(
        val fullName: String,
        val userName: String,
        val password: String
    )

    private var duplicateLoginData1: ArrayList<DuplicateUserLoginData>? = ArrayList()
    private lateinit var dataBase: UserDB
    private var userLoginData1: ArrayList<User> = ArrayList()
    private val scope = CoroutineScope(Dispatchers.IO)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        dataBase =
            Room.databaseBuilder(applicationContext, UserDB::class.java, "User_DataBase")
                .fallbackToDestructiveMigration()
                .build()

        loadUserData() // Loads user details from the database

        fullName = findViewById(R.id.et_sFullName)
        userName = findViewById(R.id.et_sUsername)
        uPass = findViewById(R.id.et_sPass)
        registerBtn = findViewById(R.id.btn_register)
        errorText = findViewById(R.id.tv_error_text)

        registerBtn.setOnClickListener {
            val fullName = fullName.text.toString()
            val userName = userName.text.toString()
            val pass = uPass.text.toString()
            var userStatus = 0
            val regex =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%&*()_+=|<>?{}\\\\~-]).{8,}".toRegex()
            if (fullName.isNotEmpty() && userName.isNotEmpty() && pass.isNotEmpty()) {
                if (pass.contains(regex)) {
                    if (duplicateLoginData1?.contains(DuplicateUserLoginData(fullName, userName, pass)) == false){
                        dataBase.userDao().insert(User(0, fullName, userName, pass))
                        userStatus = 1
                    }
                    else{
                        errorText.visibility = View.VISIBLE
                        errorText.text = "User Already Exist"
                    }
                    if (userStatus == 1) {
                        signUp()
                    } else {
                        errorText.visibility = View.VISIBLE
                        errorText.text = "$userLoginData1"
                    }
                } else {
                    errorText.visibility = View.VISIBLE
                }
            } else {
                errorText.visibility = View.VISIBLE
                errorText.text = "Enter All Details"
            }
        }

    }

    private fun signUp() {
        startActivity(
            Intent(this, DogBreedActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
    private fun loadUserData() {
        scope.launch {
            val userData = dataBase.userDao().getAllUser // Suspend function
            userData?.let {
                userLoginData1 = ArrayList(it)
                duplicateLoginData1 = userLoginData1.map { user->
                    DuplicateUserLoginData(
                        user.uFullName,
                        user.uName,
                        user.uPass
                    )
                } as ArrayList<DuplicateUserLoginData>?
            }
            Log.i("user data signup", "$duplicateLoginData1")
        }
    }

}
package com.smartgeek.designwayproject.activities.login

// This code written by SMARTGEEK

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.smartgeek.designwayproject.R
import com.smartgeek.designwayproject.activities.dogpages.DogBreedActivity
import com.smartgeek.designwayproject.model.userdata.User
import com.smartgeek.designwayproject.model.userdata.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var signupBtn: TextView
    private lateinit var userName: EditText
    private lateinit var userPass: EditText
    private lateinit var loginBtn: Button

    data class DuplicateUserLoginData(val userName: String, val password: String)
    private lateinit var errorText: TextView
    private lateinit var dataBase: UserDB
    private var userLoginData: List<User> = emptyList()
    private var duplicateLoginData: List<DuplicateUserLoginData> = emptyList()


    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dataBase = Room.databaseBuilder(applicationContext, UserDB::class.java, "User_DataBase")
            .fallbackToDestructiveMigration()
            .build()

        loadUserData()

        userName = findViewById(R.id.et_username)
        userPass = findViewById(R.id.et_pass)
        signupBtn = findViewById(R.id.tv_signup)
        loginBtn = findViewById(R.id.btn_login)
        errorText = findViewById(R.id.tv_error_text1)

        loginBtn.setOnClickListener {
            val inputUserName = userName.text.toString()
            val inputPass = userPass.text.toString()

            if (inputUserName.isNotEmpty() && inputPass.isNotEmpty()) {
                val isLoginValid = duplicateLoginData.any {
                    it.userName == inputUserName && it.password == inputPass
                }

                if (isLoginValid) {
                    startActivity(
                        Intent(this@LoginActivity, DogBreedActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    )
                } else {
                    errorText.visibility = View.VISIBLE
                    errorText.text = "Incorrect Username or Password"
                }
            } else {
                errorText.visibility = View.VISIBLE
                errorText.text = "Enter All Details"
            }
        }


        signupBtn.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }
    }

    private fun loadUserData() {
        CoroutineScope(Dispatchers.IO).launch {
            userLoginData = dataBase.userDao().getAllUser ?: emptyList()
            duplicateLoginData = userLoginData.map {
                DuplicateUserLoginData(it.uName, it.uPass)
            }
            Log.i("user data login", "$duplicateLoginData")
        }
    }

}
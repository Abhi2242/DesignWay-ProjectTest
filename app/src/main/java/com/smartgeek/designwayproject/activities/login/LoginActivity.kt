package com.smartgeek.designwayproject.activities.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smartgeek.designwayproject.R
import com.smartgeek.designwayproject.activities.dogpages.DogBreedActivity
import com.smartgeek.designwayproject.model.userdata.UserData

class LoginActivity : AppCompatActivity() {

    private lateinit var signupBtn: TextView
    private var userDataList: ArrayList<UserData>? = null

    data class DuplicateUserDataList(val userName: String, val password: String)

    private var duplicateDataList: java.util.ArrayList<DuplicateUserDataList>? = null
    private lateinit var userName: EditText
    private lateinit var userPass: EditText
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loadData()

        userName = findViewById(R.id.et_username)
        userPass = findViewById(R.id.et_pass)
        signupBtn = findViewById(R.id.tv_signup)
        loginBtn = findViewById(R.id.btn_login)

        loginBtn.setOnClickListener {
            val userName = userName.text.toString()
            val pass = userPass.text.toString()

            // This Main function to perform
            if (userName.isNotEmpty() && pass.isNotEmpty() && duplicateDataList?.contains(
                    DuplicateUserDataList(userName, pass)
                ) == true
            ) {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                startActivity(
                    Intent(this@LoginActivity, DogBreedActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            } else {
                Toast.makeText(this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show()
            }
        }

        signupBtn.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }
    }

    private fun loadData() {
        val shareData: SharedPreferences = getSharedPreferences("Shared Data", MODE_PRIVATE)
        val gson = Gson()
        val json: String? = shareData.getString("Array List", null)
        val type = object : TypeToken<ArrayList<UserData>>() {}.type
        userDataList = gson.fromJson(json, type)
        Log.i("Array List load user data", "$userDataList")

        if (userDataList?.size == null) {
            userDataList = ArrayList()
        } else {
            duplicateDataList =
                userDataList?.map {
                    DuplicateUserDataList(
                        it.userName,
                        it.uPassword
                    )
                } as java.util.ArrayList<DuplicateUserDataList>?
        }
    }
}
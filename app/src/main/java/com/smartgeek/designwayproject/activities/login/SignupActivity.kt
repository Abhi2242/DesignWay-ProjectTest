package com.smartgeek.designwayproject.activities.login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smartgeek.designwayproject.R
import com.smartgeek.designwayproject.activities.dogpages.DogBreedActivity
import com.smartgeek.designwayproject.model.userdata.UserData

class SignupActivity : AppCompatActivity() {

    private lateinit var fullName: EditText
    private lateinit var userName: EditText
    private lateinit var uPass: EditText
    private lateinit var registerBtn: Button
    private var userDataList: ArrayList<UserData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        loadData()

        fullName = findViewById(R.id.et_sFullName)
        userName = findViewById(R.id.et_sUsername)
        uPass = findViewById(R.id.et_sPass)
        registerBtn = findViewById(R.id.btn_register)

        registerBtn.setOnClickListener {
            val fullName = fullName.text.toString()
            val userName = userName.text.toString()
            val pass = uPass.text.toString()
            val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%&*()_+=|<>?{}\\\\~-]).{8,}".toRegex()
            if (pass.contains(regex)){
                if (fullName.isNotEmpty() && userName.isNotEmpty() && pass.isNotEmpty()){
//                    Toast.makeText(this, "all details present", Toast.LENGTH_SHORT).show()

                    if (userDataList?.contains(UserData(fullName,userName,pass)) == false){
                        userDataList?.size?.let { userDataList?.add(it, UserData(fullName,userName,pass)) }
                        Toast.makeText(this, "$userDataList", Toast.LENGTH_SHORT).show()
                        saveData()
                        startActivity(/* intent = */ Intent(this, DogBreedActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        )
                    }
                    else{
                        Toast.makeText(this, "User Already exist", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this, "all details not present", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, pass, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun saveData() {
        val shareData: SharedPreferences = getSharedPreferences("Shared Data", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = shareData.edit()
        val gson = Gson()
        val json: String = gson.toJson(userDataList?.distinct())
        editor.putString("Array List", json)
        editor.apply()
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
        }
    }
}
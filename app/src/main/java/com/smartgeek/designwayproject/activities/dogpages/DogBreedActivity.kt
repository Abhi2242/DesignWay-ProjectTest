package com.smartgeek.designwayproject.activities.dogpages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.smartgeek.designwayproject.R
import com.smartgeek.designwayproject.adaptor.DogBreedAdaptor
import com.smartgeek.designwayproject.model.dogbread.DogBreed
import com.smartgeek.designwayproject.services.DogBreedService
import com.smartgeek.designwayproject.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogBreedActivity : AppCompatActivity() {

    private lateinit var rvDogBeadList: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_breed)

        rvDogBeadList = findViewById(R.id.rv_dogLists)

        loadBreadDetails()
    }

    private fun loadBreadDetails() {
        val dogBreadDetails =ServiceBuilder.buildService(DogBreedService::class.java)
        val requestCall = dogBreadDetails.getBreadList()

        requestCall.enqueue(object: Callback<DogBreed>{
            override fun onResponse(call: Call<DogBreed>, response: Response<DogBreed>) {
                if (response.isSuccessful) {
                    // If status code is in the range of 200's
                    Log.i("test", "Success with ${response.code()} code")
                    val dataList = response.body()!!
                    rvDogBeadList.adapter = DogBreedAdaptor(dataList)
                }
            }

            override fun onFailure(call: Call<DogBreed>, t: Throwable) {
                Toast.makeText(this@DogBreedActivity, "Check your Internet", Toast.LENGTH_LONG).show()
            }

        })
    }
}
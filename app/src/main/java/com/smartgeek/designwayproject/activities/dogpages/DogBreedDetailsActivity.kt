package com.smartgeek.designwayproject.activities.dogpages

// This code written by SMARTGEEK

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.smartgeek.designwayproject.R
import com.smartgeek.designwayproject.model.breadimage.Breed
import com.smartgeek.designwayproject.model.breadimage.DogImage
import com.smartgeek.designwayproject.services.DogBreedService
import com.smartgeek.designwayproject.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogBreedDetailsActivity : AppCompatActivity() {

    private lateinit var bigImage: ImageView
    private lateinit var llImage: LinearLayout
    private lateinit var llMain: LinearLayout
    private lateinit var dogImage: ImageView
    private lateinit var dName: TextView
    private lateinit var dGroup: TextView
    private lateinit var dOrigin: TextView
    private lateinit var breedFor: TextView
    private lateinit var lifeSpan: TextView
    private lateinit var temperament: TextView
    private lateinit var dWidth: TextView
    private lateinit var dHeight: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_bread_details)

        bigImage = findViewById(R.id.iv_big_image)
        llImage = findViewById(R.id.ll_image)
        llMain = findViewById(R.id.ll_main)
        dogImage = findViewById(R.id.iv_dog_image)
        dName = findViewById(R.id.tv_name)
        dGroup = findViewById(R.id.tv_group)
        dOrigin = findViewById(R.id.tv_origin)
        breedFor = findViewById(R.id.tv_bred_for)
        lifeSpan = findViewById(R.id.tv_life_span)
        temperament = findViewById(R.id.tv_temperament)
        dWidth = findViewById(R.id.tv_width)
        dHeight = findViewById(R.id.tv_height)

        val bundle: Bundle? = intent.extras
        if (bundle?.containsKey(ARG_PROFILE_ID)!!) {
            val id = intent.getStringExtra(ARG_PROFILE_ID)!!
            loadDetails(id)
        }

        dogImage.setOnClickListener {
            llImage.visibility = View.VISIBLE
            llMain.visibility = View.GONE
        }

        llImage.setOnClickListener {
            llImage.visibility = View.GONE
            llMain.visibility = View.VISIBLE
        }
    }

    private fun loadDetails(imageId: String) {
        val breadDetails = ServiceBuilder.buildService(DogBreedService::class.java)
        val details = breadDetails.getBreadImage(imageId)

        details.enqueue(object : Callback<DogImage?> {
            override fun onResponse(call: Call<DogImage?>, response: Response<DogImage?>) {
                if (response.isSuccessful){
                    val dogBreadProfile = response.body()
                    val breedDetail: List<Breed>? = dogBreadProfile?.breeds
                    val url = dogBreadProfile?.url

                    Glide.with(this@DogBreedDetailsActivity)
                        .load(url)
                        .transform(CenterInside(), RoundedCorners(24))
                        .into(bigImage)

                    Glide.with(this@DogBreedDetailsActivity)
                        .load(url)
                        .override(550,350)
                        .transform(CenterInside(), RoundedCorners(24))
                        .into(dogImage)

                    dName.text = breedDetail?.get(0)?.name
                    dGroup.text = breedDetail?.get(0)?.breed_group
                    dOrigin.text = breedDetail?.get(0)?.origin
                    breedFor.text = breedDetail?.get(0)?.bred_for
                    lifeSpan.text = breedDetail?.get(0)?.life_span
                    temperament.text = breedDetail?.get(0)?.temperament
                    dWidth.text = dogBreadProfile?.width.toString()
                    dHeight.text = dogBreadProfile?.height.toString()
                }
            }

            override fun onFailure(call: Call<DogImage?>, t: Throwable) {
                Toast.makeText(this@DogBreedDetailsActivity, "Check your Internet", Toast.LENGTH_LONG).show()
            }
        })
    }

    companion object {
        const val ARG_PROFILE_ID = "id"
    }
}
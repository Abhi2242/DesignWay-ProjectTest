package com.smartgeek.designwayproject.services

import com.smartgeek.designwayproject.model.breadimage.DogImage
import com.smartgeek.designwayproject.model.dogbread.DogBreed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogBreedService {

    @GET("v1/breeds?limit=20&page=0")
    fun getBreadList(): Call<DogBreed>

    @GET("v1/images/{id}")
    fun getBreadImage(@Path("id") id: String): Call<DogImage>

}
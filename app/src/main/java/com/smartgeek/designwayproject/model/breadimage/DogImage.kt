package com.smartgeek.designwayproject.model.breadimage

data class DogImage(
    val breeds: List<Breed>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)
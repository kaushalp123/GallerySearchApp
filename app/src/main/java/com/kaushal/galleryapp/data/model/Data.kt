package com.kaushal.galleryapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @field:Json(name = "images") val images: List<Image>,
    @field:Json(name = "datetime") val datetime: Long,
    @field:Json(name = "id") val id: String
)
package com.kaushal.galleryapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageData(
    @field:Json(name = "data") val `data`: List<Data>,
    @field:Json(name = "status") val status: Int,
    @field:Json(name = "success") val success: Boolean
)
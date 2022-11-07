package com.kaushal.galleryapp.data.model

import com.squareup.moshi.Json

data class ImageData(
    @field:Json(name = "data") val `data`: List<Data>,
    @field:Json(name = "status") val status: Int,
    @field:Json(name = "success") val success: Boolean
)
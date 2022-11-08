package com.kaushal.galleryapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageResponse(
    @field:Json(name = "data") val data: List<Data>
)
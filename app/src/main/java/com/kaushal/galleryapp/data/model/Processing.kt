package com.kaushal.galleryapp.data.model

import com.squareup.moshi.Json

data class Processing(
    @field:Json(name = "status")  val status: String
)
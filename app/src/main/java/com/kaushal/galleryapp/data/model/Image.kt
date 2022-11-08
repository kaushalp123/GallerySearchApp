package com.kaushal.galleryapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    @field:Json(name = "datetime") val datetime: Long?,
    @field:Json(name = "link") val link: String?,
    @field:Json(name = "title") val title: Any?,
    @field:Json(name = "type") val type: String?
)
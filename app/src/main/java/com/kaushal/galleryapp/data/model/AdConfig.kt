package com.kaushal.galleryapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AdConfig(
    @field:Json(name = "highRiskFlags") val highRiskFlags: List<Any>,
    @field:Json(name = "safeFlags") val safeFlags: List<String>,
    @field:Json(name = "showAdLevel") val showAdLevel: Int,
    @field:Json(name = "showsAds") val showsAds: Boolean,
    @field:Json(name = "unsafeFlags") val unsafeFlags: List<String>,
    @field:Json(name = "wallUnsafeFlags") val wallUnsafeFlags: List<Any>
)
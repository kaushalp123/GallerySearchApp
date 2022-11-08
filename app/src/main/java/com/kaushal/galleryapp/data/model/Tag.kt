package com.kaushal.galleryapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tag(
    @field:Json(name = "accent") val accent: String,
    @field:Json(name = "background_hash") val background_hash: String,
    @field:Json(name = "background_is_animated") val background_is_animated: Boolean,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "description_annotations") val description_annotations: DescriptionAnnotations,
    @field:Json(name = "display_name") val display_name: String,
    @field:Json(name = "followers") val followers: Int,
    @field:Json(name = "following") val following: Boolean,
    @field:Json(name = "is_promoted") val is_promoted: Boolean,
    @field:Json(name = "is_whitelisted") val is_whitelisted: Boolean,
    @field:Json(name = "logo_destination_url") val logo_destination_url: Any,
    @field:Json(name = "logo_hash") val logo_hash: Any,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "thumbnail_hash") val thumbnail_hash: String,
    @field:Json(name = "thumbnail_is_animated") val thumbnail_is_animated: Boolean,
    @field:Json(name = "total_items") val total_items: Int
)
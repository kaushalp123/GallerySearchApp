package com.kaushal.galleryapp.data.model

import androidx.lifecycle.GeneratedAdapter
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @field:Json(name = "account_id") val account_id: Int,
    @field:Json(name = "account_url") val account_url: String,
    @field:Json(name = "ad_config") val ad_config: AdConfig,
    @field:Json(name = "ad_type") val ad_type: Int,
    @field:Json(name = "ad_url") val ad_url: String,
    @field:Json(name = "comment_count") val comment_count: Int,
    @field:Json(name = "cover") val cover: String,
    @field:Json(name = "cover_height") val cover_height: Int,
    @field:Json(name = "cover_width") val cover_width: Int,
    @field:Json(name = "datetime") val datetime: Int,
    @field:Json(name = "description") val description: Any,
    @field:Json(name = "downs") val downs: Int,
    @field:Json(name = "favorite") val favorite: Boolean,
    @field:Json(name = "favorite_count") val favorite_count: Int,
    @field:Json(name = "id") val id: String,
    @field:Json(name = "images") val images: List<Image>,
    @field:Json(name = "images_count") val images_count: Int,
    @field:Json(name = "in_gallery") val in_gallery: Boolean,
    @field:Json(name = "in_most_viral") val in_most_viral: Boolean,
    @field:Json(name = "include_album_ads") val include_album_ads: Boolean,
    @field:Json(name = "is_ad") val is_ad: Boolean,
    @field:Json(name = "is_album") val is_album: Boolean,
    @field:Json(name = "layout") val layout: String,
    @field:Json(name = "link") val link: String,
    @field:Json(name = "nsfw") val nsfw: Boolean,
    @field:Json(name = "points") val points: Int,
    @field:Json(name = "privacy") val privacy: String,
    @field:Json(name = "score") val score: Int,
    @field:Json(name = "section") val section: String,
    @field:Json(name = "tags") val tags: List<Tag>,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "topic") val topic: Any,
    @field:Json(name = "topic_id") val topic_id: Any,
    @field:Json(name = "ups") val ups: Int,
    @field:Json(name = "views") val views: Int,
    @field:Json(name = "vote") val vote: Any
)
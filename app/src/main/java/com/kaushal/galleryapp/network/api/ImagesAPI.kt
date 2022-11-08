package com.kaushal.galleryapp.network.api

import com.kaushal.galleryapp.data.model.Data
import com.kaushal.galleryapp.data.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ImagesAPI {

    @GET("gallery/search/top/week/{page}")
    suspend fun getSearchedImages(
        @Path("page") pageNo : Int,
        @Header("Authorization") clientId : String,
        @Query("q") searchText : String
    ) : ImageResponse
}
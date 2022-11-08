package com.kaushal.galleryapp.data.repo

import com.kaushal.galleryapp.data.model.Data
import com.kaushal.galleryapp.data.model.ImageResponse

interface SearchImageRepository {
    suspend fun getSearchedImages(pageNo : Int, clientId : String, searchedText : String) : ImageResponse
}
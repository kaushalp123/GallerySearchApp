package com.kaushal.galleryapp.data.repo

import com.kaushal.galleryapp.data.model.Data

interface SearchImageRepository {
    suspend fun getSearchedImages(pageNo : Int, clientId : String, searchedText : String) : Data
}
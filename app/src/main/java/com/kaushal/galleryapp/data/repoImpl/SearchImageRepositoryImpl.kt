package com.kaushal.galleryapp.data.repoImpl

import com.kaushal.galleryapp.data.model.Data
import com.kaushal.galleryapp.data.repo.SearchImageRepository
import com.kaushal.galleryapp.network.api.ImagesAPI
import com.kaushal.galleryapp.network.services.RetrofitService

class SearchImageRepositoryImpl : SearchImageRepository{

    override suspend fun getSearchedImages(pageNo: Int, clientId : String, searchedText: String): Data {
        val retrofitInstance = RetrofitService.getRetrofitInstance()
        val apiService = retrofitInstance.create(ImagesAPI::class.java)

        return apiService.getSearchedImages(pageNo, clientId, searchedText)
    }
}
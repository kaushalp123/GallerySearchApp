package com.kaushal.galleryapp.data.repoImpl

import com.kaushal.galleryapp.data.model.ImageResponse
import com.kaushal.galleryapp.data.repo.SearchImageRepository
import com.kaushal.galleryapp.network.api.ImagesAPI
import com.kaushal.galleryapp.network.services.RetrofitService

class SearchImageRepositoryImpl : SearchImageRepository {

    override suspend fun getSearchedImages(
        pageNo: Int,
        clientId: String,
        searchedText: String
    ): ImageResponse {
        val retrofitInstance = RetrofitService.getRetrofitInstance()
        val apiService = retrofitInstance.create(ImagesAPI::class.java) // getting the retrofit instance

        return apiService.getSearchedImages(pageNo, "Client-ID $clientId", searchedText) // api call

    }
}
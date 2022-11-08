package com.kaushal.galleryapp.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaushal.galleryapp.data.repo.SearchImageRepository
import com.kaushal.galleryapp.util.APIErrorHandler

class MainActivityViewModelFactory(private val searchImageRepository: SearchImageRepository,
                                   private val apiErrorHandler: APIErrorHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(searchImageRepository, apiErrorHandler) as T
    }
}
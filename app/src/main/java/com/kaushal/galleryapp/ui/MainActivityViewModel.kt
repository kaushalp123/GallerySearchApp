package com.kaushal.galleryapp.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaushal.galleryapp.data.model.Data
import com.kaushal.galleryapp.data.repo.SearchImageRepository
import com.kaushal.galleryapp.util.APIErrorHandler
import com.kaushal.galleryapp.util.Outcome
import com.kaushal.galleryapp.util.failure
import com.kaushal.galleryapp.util.success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel constructor(
    private val searchImageRepository: SearchImageRepository,
    private val stateHandle: SavedStateHandle,
    private val apiErrorHandler: APIErrorHandler
) : ViewModel() {

    private val clientId: String
        get() = stateHandle["clientId"]
            ?: throw IllegalArgumentException("client id is missing!")

    private val searchedText: String
        get() = stateHandle["searchedText"]
            ?: throw IllegalArgumentException("Search text is empty!")

    private val pageNo: Int
        get() = stateHandle["pageNo"]!!

    private val _searchedResult = MutableStateFlow<Outcome<Data>>(Outcome.Empty)
    val searchedResult = _searchedResult.asStateFlow()

    fun fetchImages() {
        viewModelScope.launch {
            try {
                val result = searchImageRepository.getSearchedImages(pageNo, clientId, searchedText)
                _searchedResult.success(result)
            } catch (e: java.lang.Exception) {
               _searchedResult.failure(e, apiErrorHandler.errMessage(e))
            }
        }
    }

}
package com.kaushal.galleryapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaushal.galleryapp.data.model.ImageResponse
import com.kaushal.galleryapp.data.repo.SearchImageRepository
import com.kaushal.galleryapp.util.APIErrorHandler
import com.kaushal.galleryapp.util.Outcome
import com.kaushal.galleryapp.util.failure
import com.kaushal.galleryapp.util.success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainActivityViewModel constructor(
    private val searchImageRepository: SearchImageRepository,
    private val apiErrorHandler: APIErrorHandler
) : ViewModel() {

    private val _searchedResult = MutableStateFlow<Outcome<ImageResponse>>(Outcome.Empty)
    val searchedResult = _searchedResult.mapLatest {
        if (it is Outcome.Empty) null else it
    }.stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = null)

    fun fetchImages(clientId: String, searchedText: String?, pageNo: Int) {

        try {
            if (searchedText.isNullOrEmpty()) {
                _searchedResult.failure(
                    java.lang.IllegalArgumentException(),
                    "search query cannot be empty!"
                )
                return
            }

            viewModelScope.launch {
                try {
                    val result = searchImageRepository.getSearchedImages(pageNo, clientId, searchedText)
                    _searchedResult.success(result, result.data.isEmpty())
                } catch (e: java.lang.Exception) {
                    _searchedResult.failure(e, apiErrorHandler.errMessage(e))
                }
            }
        } catch (e: Exception) {
        }
    }

}
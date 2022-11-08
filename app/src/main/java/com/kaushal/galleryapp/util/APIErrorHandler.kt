package com.kaushal.galleryapp.util

import android.accounts.NetworkErrorException
import android.content.Context
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException

class APIErrorHandler() {

    // this function accepts the Throwable type as an argument and provides the appropriate error message by checking its type, default error message is returned otherwise.
    fun errMessage(error: Throwable) : String {
        return when (error) {
            is HttpException -> "Server Error occurred!"
            is NetworkErrorException -> "Please check your internet connection!"
            is JsonDataException -> "There was a problem retrieving your search results!"
            else -> when {
                !error.localizedMessage.isNullOrEmpty() -> error.localizedMessage.orEmpty()
                !error.message.isNullOrEmpty() -> error.message.orEmpty()
                else -> "Something went wrong!"
            }
        }
    }
}
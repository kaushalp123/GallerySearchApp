package com.kaushal.galleryapp.util

import android.accounts.NetworkErrorException
import android.content.Context
import retrofit2.HttpException

class APIErrorHandler(private val context: Context) {

    // this function accepts the Throwable type as an argument and provides the appropriate error message by checking its type, default error message is returned otherwise.
    fun errMessage(error: Throwable) : String {
        return when (error) {
            is HttpException -> "API Exception"
            is NetworkErrorException -> "Please check your internet connection"
            else -> when {
                !error.localizedMessage.isNullOrEmpty() -> error.localizedMessage.orEmpty()
                !error.message.isNullOrEmpty() -> error.message.orEmpty()
                else -> "Something went wrong!"
            }
        }
    }
}
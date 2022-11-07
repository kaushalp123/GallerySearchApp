package com.kaushal.galleryapp.util


// this sealed class confirms that the api response type will be one of the below types.
sealed class Outcome<out T> {

    data class Success<out T>(val data: T, val isEmptyList: Boolean) : Outcome<T>()

    data class Failure(val e: Throwable, val errMsg: String) : Outcome<Nothing>()

    data class Loading(val asOverlay: Boolean = false) : Outcome<Nothing>()

    object Empty : Outcome<Nothing>()

    companion object {
        fun loading(asOverlay: Boolean = false): Outcome<Nothing> = Loading(asOverlay)

        fun <T> success(data: T, isEmptyList: Boolean = false): Outcome<T> =
            Success(data, isEmptyList)

        fun failure(e: Throwable, errMsg: String): Outcome<Nothing> = Failure(e, errMsg)
    }
}
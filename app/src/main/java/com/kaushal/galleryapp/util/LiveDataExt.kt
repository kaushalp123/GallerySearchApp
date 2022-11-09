package com.kaushal.galleryapp.util

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<Outcome<T>>.loading() { // states that data is still loading and the loader needs to be displayed on the screen
    value = Outcome.loading()
}

fun <T> MutableStateFlow<Outcome<T>>.success(
    data: T,
    isEmpty: Boolean = false
) { // states the api call is success
    value = Outcome.success(data, isEmpty)
}

fun <T> MutableStateFlow<Outcome<T>>.failure(
    e: Throwable,
    errMsg: String
) { // states that api call is failed
    value = Outcome.failure(e, errMsg)
}

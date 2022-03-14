package com.cherish.randomfactproject.data.remote

sealed class ResponseManager<out T> {
    data class Success<T>(val data : T) : ResponseManager<T>()
    data class Failure(val message: String?, val throwable: Throwable? = null): ResponseManager<Nothing>()
    data class Loading(val status: Boolean = true): ResponseManager<Nothing>()
}
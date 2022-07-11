package com.vandoc.iptv.base

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<T>(val data: T?) : Resource<T>()
    sealed class Error : Resource<Nothing>() {
        data class BadRequest(val message: String) : Error()
        data class ServerError(val message: String) : Error()
        data class ConnectionTimeout(val message: String) : Error()
        data class ConnectionError(val message: String) : Error()
        data class Unknown(val message: String) : Error()
    }
}
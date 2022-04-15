package com.imran.tvmaze.core.network

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String, val status : Int) : Result<Nothing>()
    data class Failure(val message: String) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[message=$message, status=$status]"
            is Failure -> "Failure[failure=$message]"
        }
    }
}
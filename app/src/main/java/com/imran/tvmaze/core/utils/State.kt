package com.imran.tvmaze.core.utils

sealed class State<out R> {

    object Loading : State<Nothing>()
    class Loaded<out T>(val data: T) : State<T>()
    class Empty(val message: String) : State<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Loaded<*> -> "Success[data=$data]"
            is Empty -> "Empty[message=$message]"
            is Loading -> "Loading"
            else -> ""
        }
    }
}
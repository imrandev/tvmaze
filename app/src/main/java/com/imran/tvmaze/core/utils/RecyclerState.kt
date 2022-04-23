package com.imran.tvmaze.core.utils

sealed class RecyclerState<out R> {
    object Loading : RecyclerState<Nothing>()
    object Infinite : RecyclerState<Nothing>()
    object Loaded : RecyclerState<Nothing>()
}

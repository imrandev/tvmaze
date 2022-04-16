package com.imran.tvmaze.core.network

import retrofit2.Response

interface DataSource<T> {
    suspend fun fetch(request: suspend
        () -> Response<T>, defaultErrorMessage: String): Result<T>
}
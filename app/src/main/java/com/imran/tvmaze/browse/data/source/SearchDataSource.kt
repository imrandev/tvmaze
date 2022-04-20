package com.imran.tvmaze.browse.data.source

import com.imran.tvmaze.browse.data.model.SearchResponse
import com.imran.tvmaze.core.network.ApiService
import com.imran.tvmaze.core.network.DataSource
import com.imran.tvmaze.core.network.Result
import com.imran.tvmaze.core.utils.Constant
import com.imran.tvmaze.core.utils.ErrorUtils
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class SearchDataSource @Inject constructor(
    private val apiService: ApiService, private val retrofit: Retrofit
) : DataSource<SearchResponse> {

    suspend fun search(query: String) = fetch(
        request = {apiService.searchShows(query)},
        defaultErrorMessage = Constant.DEFAULT_GET_ERROR_MESSAGE
    )

    override suspend fun fetch(
        request: suspend () -> Response<SearchResponse>,
        defaultErrorMessage: String
    ): Result<SearchResponse> {
        return try {
            println("I'm working in thread ${Thread.currentThread().name}")
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body())
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Result.error(errorResponse?.message ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            Result.error("Unknown Error", null)
        }
    }
}
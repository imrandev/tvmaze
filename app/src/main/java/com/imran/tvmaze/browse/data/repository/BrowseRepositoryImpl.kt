package com.imran.tvmaze.browse.data.repository

import com.imran.tvmaze.browse.data.model.BrowseResponse
import com.imran.tvmaze.browse.domain.repository.BrowseRepository
import com.imran.tvmaze.core.network.ApiService
import com.imran.tvmaze.core.network.Result
import com.imran.tvmaze.core.utils.ErrorUtils
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Imran Khan on 12/26/2020.
 * Email : context.imran@gmail.com
 */

class BrowseRepositoryImpl @Inject constructor(private val apiService: ApiService, private val retrofit: Retrofit) : BrowseRepository {

    companion object {
        private const val TAG = "BrowseRepository"
    }

    override suspend fun findTVByQuery(queryParam: String): Result<BrowseResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun findTVByPage(page: String): Result<BrowseResponse>{
        return getResponse(
            request = {apiService.findShows(page)},
            defaultErrorMessage = "Error fetching tv shows"
        )
    }

    private suspend fun <BrowseResponse> getResponse(request: suspend () -> Response<BrowseResponse>, defaultErrorMessage: String): Result<BrowseResponse> {
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
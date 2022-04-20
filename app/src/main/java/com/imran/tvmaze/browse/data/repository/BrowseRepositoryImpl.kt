package com.imran.tvmaze.browse.data.repository

import com.imran.tvmaze.browse.data.model.BrowseResponse
import com.imran.tvmaze.browse.data.model.SearchResponse
import com.imran.tvmaze.browse.data.source.BrowseDataSource
import com.imran.tvmaze.browse.data.source.SearchDataSource
import com.imran.tvmaze.browse.domain.repository.BrowseRepository
import com.imran.tvmaze.core.network.Result
import javax.inject.Inject

/**
 * Created by Imran Khan on 12/26/2020.
 * Email : context.imran@gmail.com
 */

class BrowseRepositoryImpl @Inject constructor(private val browseDataSource: BrowseDataSource, private val searchDataSource: SearchDataSource) : BrowseRepository {

    companion object {
        private const val TAG = "BrowseRepository"
    }

    override suspend fun findTVByQuery(queryParam: String): Result<SearchResponse> {
        return searchDataSource.search(queryParam)
    }

    override suspend fun findTVByPage(page: String): Result<BrowseResponse>{
        return browseDataSource.fetchShows(page)
    }

    /*private suspend fun <BrowseResponse> getResponse(
        request: suspend () -> Response<BrowseResponse>, defaultErrorMessage: String): Result<BrowseResponse> {
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
    }*/
}
package com.imran.tvmaze.browse.data.repository

import com.imran.tvmaze.browse.data.model.BrowseResponse
import com.imran.tvmaze.browse.domain.repository.BrowseRepository
import com.imran.tvmaze.core.network.ApiService
import com.imran.tvmaze.core.network.EnqueueResponse
import com.imran.tvmaze.core.network.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Imran Khan on 12/26/2020.
 * Email : context.imran@gmail.com
 */

class BrowseRepositoryImpl @Inject constructor(private val apiService: ApiService) : BrowseRepository {

    companion object {
        private const val TAG = "BrowseRepository"
    }

    override fun findTVByQuery(queryParam: String): Flow<Result<BrowseResponse>> {
        TODO("Not yet implemented")
    }

    override fun findTVByPage(page: String): Flow<Result<BrowseResponse>> = flow {
        apiService.findShows(page).enqueue(object : EnqueueResponse<BrowseResponse>() {
            override fun onResult(result: Result<BrowseResponse>) {
                CoroutineScope(Dispatchers.IO).launch {
                    emit(result)
                }
            }
        })
    }
}
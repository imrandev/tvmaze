package com.imran.tvmaze.browse.domain.repository

import com.imran.tvmaze.browse.data.model.BrowseResponse
import com.imran.tvmaze.core.network.Result
import kotlinx.coroutines.flow.Flow

interface BrowseRepository {
    fun findTVByQuery(queryParam : String) : Flow<Result<BrowseResponse>>
    fun findTVByPage(page : String) : Flow<Result<BrowseResponse>>
}
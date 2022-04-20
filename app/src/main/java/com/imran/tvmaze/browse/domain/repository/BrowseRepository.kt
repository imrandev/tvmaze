package com.imran.tvmaze.browse.domain.repository

import com.imran.tvmaze.browse.data.model.BrowseResponse
import com.imran.tvmaze.browse.data.model.SearchResponse
import com.imran.tvmaze.core.network.Result

interface BrowseRepository {
    suspend fun findTVByQuery(queryParam : String) : Result<SearchResponse>
    suspend fun findTVByPage(page : String) : Result<BrowseResponse>
}
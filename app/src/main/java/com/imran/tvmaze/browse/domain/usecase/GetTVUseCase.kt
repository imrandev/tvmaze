package com.imran.tvmaze.browse.domain.usecase

import com.imran.tvmaze.browse.data.model.BrowseResponse
import com.imran.tvmaze.browse.domain.repository.BrowseRepository
import com.imran.tvmaze.core.network.Result
import com.imran.tvmaze.core.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class GetTVUseCase @Inject constructor(private val browseRepository: BrowseRepository) {

    suspend fun execute(page: String) = flow {
        emit(Result.loading())
        val result = browseRepository.findTVByPage(page)
        emit(result)
    }.flowOn(Dispatchers.IO)
}
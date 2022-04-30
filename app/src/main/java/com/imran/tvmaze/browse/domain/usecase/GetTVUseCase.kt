package com.imran.tvmaze.browse.domain.usecase

import com.imran.tvmaze.browse.domain.repository.BrowseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class GetTVUseCase @Inject constructor(private val browseRepository: BrowseRepository) {

    suspend fun execute(page: String) = flow {
        val result = browseRepository.findTVByPage(page)
        emit(result)
    }.flowOn(Dispatchers.IO)
}
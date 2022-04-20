package com.imran.tvmaze.browse.domain.usecase

import com.imran.tvmaze.browse.domain.repository.BrowseRepository
import com.imran.tvmaze.core.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.streams.toList

class SearchTVUseCase @Inject constructor(private val browseRepository: BrowseRepository) {

    suspend fun execute(query: String) = flow {
        emit(Result.loading())
        val result = browseRepository.findTVByQuery(query)
        if (result.status == Result.Status.SUCCESS){
            val data = result.data?.map { d -> d.show }
            emit(Result.success(data = data))
        }
    }.flowOn(Dispatchers.IO)
}
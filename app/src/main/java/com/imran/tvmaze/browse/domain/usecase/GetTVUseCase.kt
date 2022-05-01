package com.imran.tvmaze.browse.domain.usecase

import com.imran.tvmaze.browse.domain.repository.BrowseRepository
import com.imran.tvmaze.core.db.domain.repository.BookmarkRepository
import com.imran.tvmaze.core.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class GetTVUseCase @Inject constructor(private val browseRepository: BrowseRepository, private val bookmarkRepository: BookmarkRepository) {

    suspend fun invoke(page: String) = flow {
        val result = browseRepository.findTVByPage(page)
        val data = result.data?.map { show ->
            show.isFavorite = bookmarkRepository.findById(show.id) != null
            show
        }?.toList()
        emit(Result.success(data))
    }.flowOn(Dispatchers.IO)
}
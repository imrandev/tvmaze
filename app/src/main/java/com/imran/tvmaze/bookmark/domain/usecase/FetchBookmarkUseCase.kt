package com.imran.tvmaze.bookmark.domain.usecase

import com.imran.tvmaze.core.db.domain.repository.BookmarkRepository
import javax.inject.Inject

class FetchBookmarkUseCase @Inject constructor(private val bookmarkRepository: BookmarkRepository) {
    suspend fun invoke() = bookmarkRepository.findAll()
}
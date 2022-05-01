package com.imran.tvmaze.browse.domain.usecase

import com.imran.tvmaze.browse.domain.repository.BrowseRepository
import com.imran.tvmaze.core.base.BaseUseCase
import com.imran.tvmaze.core.db.domain.repository.BookmarkRepository
import javax.inject.Inject

class BrowseUseCase @Inject constructor(private val browseRepository: BrowseRepository, private val bookmarkRepository: BookmarkRepository) : BaseUseCase {
    val getTVUseCase = GetTVUseCase(browseRepository, bookmarkRepository)
    val searchTVUseCase = SearchTVUseCase(browseRepository, bookmarkRepository)
}
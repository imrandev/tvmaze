package com.imran.tvmaze.browse.domain.usecase

import com.imran.tvmaze.browse.domain.repository.BrowseRepository
import com.imran.tvmaze.core.base.BaseUseCase
import javax.inject.Inject

class BrowseUseCase @Inject constructor(private val browseRepository: BrowseRepository) : BaseUseCase {
    val getTVUseCase = GetTVUseCase(browseRepository)
    val searchTVUseCase = SearchTVUseCase(browseRepository)
}
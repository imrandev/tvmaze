package com.imran.tvmaze.info.domain.usecase

import com.imran.tvmaze.core.db.domain.usecase.DeleteBookmarkUseCase
import com.imran.tvmaze.core.db.domain.usecase.InsertBookmarkUseCase
import com.imran.tvmaze.core.db.domain.usecase.InsertGenreUseCase

data class InfoUseCase(
    val insertGenreUseCase: InsertGenreUseCase,
    val insertBookmarkUseCase: InsertBookmarkUseCase,
    val deleteBookmarkUseCase: DeleteBookmarkUseCase
)

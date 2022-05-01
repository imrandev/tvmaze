package com.imran.tvmaze.core.db.domain.usecase

import com.imran.tvmaze.core.base.model.ErrorResponse
import com.imran.tvmaze.core.db.domain.model.Bookmark
import com.imran.tvmaze.core.db.domain.repository.BookmarkRepository
import com.imran.tvmaze.core.db.domain.repository.GenreRepository
import com.imran.tvmaze.core.network.Result
import com.imran.tvmaze.core.utils.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import java.lang.Exception
import javax.inject.Inject

class InsertBookmarkUseCase @Inject constructor(private val bookmarkRepository: BookmarkRepository, private val genreRepository: GenreRepository) {

    suspend fun invoke(bookmark: Bookmark) = flow {
        try {
            var data = bookmarkRepository.findById(bookmark.id)
            if (data == null){
                data = bookmarkRepository.insert(bookmark)
                if (data != null && data.id > 0){
                    updateGenrePoint(bookmark, 1.0f)
                    emit(Result.success(true, Message.BOOKMARK_ADDED))
                }
            } else {
                if (bookmark.id == data.id && bookmarkRepository.delete(data)){
                    updateGenrePoint(bookmark, -1.0f)
                    emit(Result.success(false, Message.BOOKMARK_REMOVED))
                }
            }
        } catch (ex: Exception){
            emit(Result.error(Message.ERROR_DEFAULT, ErrorResponse(status = -1, ex.message)))
        }
    }.onStart {
        emit(Result.loading())
    }.flowOn(Dispatchers.IO)

    private suspend fun updateGenrePoint(bookmark: Bookmark, points: Float){
        if (bookmark.genres != null && bookmark.genres.isNotEmpty()){
            val genres = bookmark.genres.trim().splitToSequence(", ").filter { it.isNotEmpty() }.toList()
            for (genre in genres){
                genreRepository.updatePointsByName(genre, points)
            }
        }
    }
}
package com.imran.tvmaze.core.db.domain.usecase

import com.imran.tvmaze.core.base.model.ErrorResponse
import com.imran.tvmaze.core.db.domain.model.Bookmark
import com.imran.tvmaze.core.db.domain.repository.BookmarkRepository
import com.imran.tvmaze.core.network.Result
import com.imran.tvmaze.core.utils.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import java.lang.Exception
import javax.inject.Inject

class DeleteBookmarkUseCase @Inject constructor(private val bookmarkRepository: BookmarkRepository) {

    suspend fun invoke(bookmark: Bookmark) = flow {
        try {
            val data = bookmarkRepository.findById(bookmark.id)
            if (data != null && data.id == bookmark.id && bookmarkRepository.delete(bookmark)){
                emit(Result.success(true, Message.BOOKMARK_REMOVED))
            } else {
                emit(Result.success(false, "No record found, unable to delete"))
            }
        } catch (ex: Exception) {
            emit(Result.error(Message.ERROR_DEFAULT, ErrorResponse(status = -1, ex.message)))
        }
    }.onStart {
        emit(Result.loading())
    }.flowOn(Dispatchers.IO)
}
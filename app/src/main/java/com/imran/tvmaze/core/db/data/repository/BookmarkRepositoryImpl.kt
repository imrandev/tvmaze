package com.imran.tvmaze.core.db.data.repository

import com.imran.tvmaze.core.db.data.source.RoomDataSource
import com.imran.tvmaze.core.db.domain.model.Bookmark
import com.imran.tvmaze.core.db.domain.repository.BookmarkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BookmarkRepositoryImpl(private val roomDataSource: RoomDataSource) : BookmarkRepository {

    override suspend fun insert(model: Bookmark) : Bookmark? {
        roomDataSource.favoriteDao().insert(model)
        return findById(id = model.id)
    }

    override suspend fun findById(id: Int): Bookmark? {
        return roomDataSource.favoriteDao().findById(id)
    }

    override suspend fun findAll(): Flow<List<Bookmark>?> {
        return flow {
            emit(roomDataSource.favoriteDao().findAll())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun delete(model: Bookmark) : Boolean {
        roomDataSource.favoriteDao().delete(model)
        return findById(model.id) == null
    }
}
package com.imran.tvmaze.core.db.domain.repository

import kotlinx.coroutines.flow.Flow

interface RoomRepository<T> : DBRepository {

    suspend fun insert(model: T) : T?

    suspend fun findById(id: Int) : T?

    suspend fun findAll() : Flow<List<T>?>

    suspend fun delete(model: T) : Boolean
}
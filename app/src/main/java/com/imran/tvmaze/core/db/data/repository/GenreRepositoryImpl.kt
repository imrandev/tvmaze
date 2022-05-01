package com.imran.tvmaze.core.db.data.repository

import com.imran.tvmaze.core.db.data.source.RoomDataSource
import com.imran.tvmaze.core.db.domain.model.Genre
import com.imran.tvmaze.core.db.domain.repository.GenreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GenreRepositoryImpl(private val roomDataSource: RoomDataSource) : GenreRepository {

    override suspend fun findByName(name: String): Genre? {
        return roomDataSource.genreDao().findByName(name)
    }

    override suspend fun updatePointsByName(name: String, points: Float): Boolean {
        val genre = findByName(name)
        if (genre != null){
            genre.points = genre.points + points
            insert(genre)
        }
        return genre != null && genre.points == points
    }

    override suspend fun updatePriorityByName(name: String, priority: Int): Boolean {
        val genre = findByName(name)
        if (genre != null){
            genre.priority = priority
            insert(genre)
        }
        return genre != null && genre.priority == priority
    }

    override suspend fun updatePointsById(genreId: Int, points: Float): Boolean {
        val genre = findById(genreId)
        if (genre != null){
            genre.points = genre.points + points
            insert(genre)
        }
        return genre != null && genre.points == points
    }

    override suspend fun updatePriorityById(genreId: Int, priority: Int): Boolean {
        val genre = findById(genreId)
        if (genre != null){
            genre.priority = priority
            insert(genre)
        }
        return genre != null && genre.priority == priority
    }

    override suspend fun insert(model: Genre) : Genre? {
        roomDataSource.genreDao().insert(model)
        return findByName(model.name)
    }

    override suspend fun findById(id: Int): Genre? {
        return roomDataSource.genreDao().findById(id)
    }

    override suspend fun findAll(): Flow<List<Genre>?> {
        return flow {
            emit(roomDataSource.genreDao().findAll())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun delete(model: Genre) : Boolean {
        roomDataSource.genreDao().delete(model)
        return findById(id = model.id) == null
    }
}
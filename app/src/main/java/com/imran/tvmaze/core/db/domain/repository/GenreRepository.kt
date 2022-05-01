package com.imran.tvmaze.core.db.domain.repository

import com.imran.tvmaze.core.db.domain.model.Genre

interface GenreRepository : RoomRepository<Genre> {

    suspend fun findByName(name: String) : Genre?

    suspend fun updatePointsByName(name: String, points: Float) : Boolean

    suspend fun updatePriorityByName(name: String, priority: Int) : Boolean

    suspend fun updatePointsById(genreId: Int, points: Float) : Boolean

    suspend fun updatePriorityById(genreId: Int, priority: Int) : Boolean
}
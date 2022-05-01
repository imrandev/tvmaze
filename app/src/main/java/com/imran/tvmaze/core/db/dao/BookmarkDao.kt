package com.imran.tvmaze.core.db.dao

import androidx.room.*
import com.imran.tvmaze.core.db.domain.model.Bookmark

@Dao
interface BookmarkDao {

    @Query("select * from bookmark")
    suspend fun findAll() : List<Bookmark>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmark: Bookmark)

    @Delete
    suspend fun delete(bookmark: Bookmark)

    @Query("select * from bookmark where id = :id")
    suspend fun findById(id: Int): Bookmark?
}
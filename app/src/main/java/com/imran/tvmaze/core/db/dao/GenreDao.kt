package com.imran.tvmaze.core.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.imran.tvmaze.core.db.domain.model.Genre

@Dao
interface GenreDao {

    @Query("select * from genre")
    suspend fun findAll() : List<Genre>

    @Insert(onConflict = REPLACE)
    suspend fun insert(genre: Genre)

    @Delete
    suspend fun delete(genre: Genre)

    @Query("select * from genre where name = :name")
    suspend fun findByName(name: String) : Genre?

    @Query("select * from genre where id = :id")
    suspend fun findById(id: Int) : Genre?
}

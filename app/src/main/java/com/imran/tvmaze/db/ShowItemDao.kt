package com.imran.tvmaze.db

import androidx.room.*
import com.imran.tvmaze.model.ShowsItem
import kotlinx.coroutines.flow.Flow

/**
 * Created by Imran Khan on 1/2/2021.
 * Email : context.imran@gmail.com
 */

@Dao
interface ShowItemDao {

    @Query("select * from ShowsItem")
    fun findAll() : Flow<List<ShowsItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(item: ShowsItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(items: List<ShowsItem>)

    @Update
    suspend fun update(item: ShowsItem)

    @Delete
    suspend fun delete(item: ShowsItem)
}
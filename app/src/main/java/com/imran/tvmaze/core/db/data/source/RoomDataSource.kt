package com.imran.tvmaze.core.db.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.imran.tvmaze.BuildConfig
import com.imran.tvmaze.core.db.dao.BookmarkDao
import com.imran.tvmaze.core.db.dao.GenreDao
import com.imran.tvmaze.core.db.domain.model.Bookmark
import com.imran.tvmaze.core.db.domain.model.Genre

@Database(
    entities = [Bookmark::class, Genre::class], version = BuildConfig.VERSION_CODE
)
abstract class RoomDataSource : RoomDatabase(), DataSource {

    /*companion object {
        @Volatile private var instance: RoomService? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            RoomService::class.java, Constant.DB_NAME)
            .build()
    }*/

    abstract fun favoriteDao() : BookmarkDao

    abstract fun genreDao() : GenreDao
}
package com.imran.tvmaze.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.imran.tvmaze.BuildConfig
import com.imran.tvmaze.core.db.model.Favorite
import com.imran.tvmaze.core.utils.Constant

@Database(
    entities = [Favorite::class], version = BuildConfig.VERSION_CODE
)
abstract class RoomService : RoomDatabase() {

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
}
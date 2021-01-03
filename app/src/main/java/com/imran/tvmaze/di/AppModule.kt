package com.imran.tvmaze.di

import android.content.Context
import androidx.room.Room
import com.imran.tvmaze.db.RoomManager
import com.imran.tvmaze.network.RetrofitClient
import com.imran.tvmaze.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Created by Imran Khan on 1/2/2021.
 * Email : context.imran@gmail.com
 */

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideApiRepository(
        @Named("tvMaze") baseUrl: String,
        @ApplicationContext context: Context
    ) = RetrofitClient.getInstance(baseUrl, context)

    @Provides
    @Singleton
    @Named("tvMaze")
    fun provideBaseUrl() = Constant.TV_MAZE_BASE_URL

    @Provides
    @Singleton
    fun provideRoomManger(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, RoomManager::class.java, Constant.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}
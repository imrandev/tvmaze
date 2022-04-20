package com.imran.tvmaze.core.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.imran.tvmaze.BuildConfig
import com.imran.tvmaze.browse.data.repository.BrowseRepositoryImpl
import com.imran.tvmaze.browse.data.source.BrowseDataSource
import com.imran.tvmaze.browse.data.source.SearchDataSource
import com.imran.tvmaze.browse.domain.repository.BrowseRepository
import com.imran.tvmaze.browse.domain.usecase.BrowseUseCase
import com.imran.tvmaze.core.network.ApiService
import com.imran.tvmaze.core.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideBaseUrl() : String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideGson() : Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context) : Cache = Cache(context.cacheDir, Constant.cacheSize)

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache, @ApplicationContext context: Context) = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork(context)!!)
                    request.newBuilder()
                        .header("Cache-Control", "public, max-age=" + 5)
                        .build()
                else
                    request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                        .build()
                chain.proceed(request)
            }
            .build()
    } else OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiRepository(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    private fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    @Provides
    @Singleton
    fun provideBrowseDataSource(apiService: ApiService, retrofit: Retrofit) : BrowseDataSource = BrowseDataSource(apiService, retrofit)

    @Provides
    @Singleton
    fun provideSearchDataSource(apiService: ApiService, retrofit: Retrofit) : SearchDataSource = SearchDataSource(apiService, retrofit)

    @Provides
    @Singleton
    fun provideBrowseRepository(browseDataSource: BrowseDataSource, searchDataSource: SearchDataSource) : BrowseRepository = BrowseRepositoryImpl(browseDataSource, searchDataSource)

    @Provides
    @Singleton
    fun provideBrowseUseCase(browseRepository: BrowseRepository) : BrowseUseCase = BrowseUseCase(browseRepository)
}
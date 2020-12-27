package com.imran.tvmaze.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Imran Khan on 12/25/2020.
 * Email : context.imran@gmail.com
 */

object RetrofitClient {

    private const val TAG = "RetrofitClient"

    private const val cacheSize = (5 * 1024 * 1024).toLong()

    fun getInstance(baseURL: String, context: Context):
            ApiRepository = provideRetrofit(provideGson(), baseURL, context).create(ApiRepository::class.java)

    private fun provideGson() = GsonBuilder().setLenient().create()

    private fun provideRetrofit(gson: Gson, baseUrl: String, context: Context) = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(provideOkHttp(context))
            .build()

    private fun provideCache(context: Context) = Cache(context.cacheDir, cacheSize)

    private fun provideOkHttp(context: Context) = OkHttpClient.Builder()
        .cache(provideCache(context))
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

    private fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}
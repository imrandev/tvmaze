package com.imran.tvmaze.core.network

import com.imran.tvmaze.browse.data.model.BrowseResponse
import com.imran.tvmaze.browse.data.model.SearchResponse
import com.imran.tvmaze.core.base.model.Episode
import com.imran.tvmaze.core.base.model.Show
import com.imran.tvmaze.dashboard.data.model.UpcomingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Imran Khan on 12/25/2020.
 * Email : context.imran@gmail.com
 */

interface ApiService {

    @GET("/shows")
    suspend fun findShows(@Query("page") page : String): Response<BrowseResponse>

    @GET("/search/shows")
    suspend fun searchShows(@Query("q") query: String): Response<SearchResponse>

    @GET("/schedule")
    suspend fun upcomingShows(): Response<UpcomingResponse>

    @GET("/shows/{id}/episodes")
    suspend fun findEpisodesById(@Path("id") id: String) : Response<List<Episode>>

    @GET("/shows/{id}")
    suspend fun findShowById(@Path("id") id: String) : Response<Show>
}
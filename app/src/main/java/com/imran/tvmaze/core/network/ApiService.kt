package com.imran.tvmaze.core.network

import com.imran.tvmaze.browse.data.model.BrowseResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Imran Khan on 12/25/2020.
 * Email : context.imran@gmail.com
 */

interface ApiService {

    @GET("/shows")
    suspend fun findShows(@Query("page") page : String): Response<BrowseResponse>
}
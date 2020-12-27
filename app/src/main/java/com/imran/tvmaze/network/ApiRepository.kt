package com.imran.tvmaze.network

import com.imran.tvmaze.model.Shows
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Imran Khan on 12/25/2020.
 * Email : context.imran@gmail.com
 */

interface ApiRepository {

    @GET("/shows")
    fun findShows(): Call<Shows>
}
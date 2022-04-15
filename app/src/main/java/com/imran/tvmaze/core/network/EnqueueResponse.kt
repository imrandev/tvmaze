package com.imran.tvmaze.core.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Imran Khan on 12/25/2020.
 * Email : context.imran@gmail.com
 */

public abstract class EnqueueResponse<T> : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        val code = response.code()
        val message = response.raw().message;
        if (response.isSuccessful){
            val result = response.body()
            if (result != null){
                onResult(Result.Success(result))
            } else {
                onResult(Result.Error(message, code))
            }
        } else {
            onResult(Result.Failure(message))
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onResult(Result.Failure(t.message!!))
    }

    abstract fun onResult(result: Result<T>)
}
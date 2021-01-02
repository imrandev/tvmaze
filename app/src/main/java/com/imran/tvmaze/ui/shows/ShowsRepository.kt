package com.imran.tvmaze.ui.shows

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.imran.tvmaze.model.Shows
import com.imran.tvmaze.network.ApiRepository
import com.imran.tvmaze.network.EnqueueResponse
import com.imran.tvmaze.network.RetrofitClient
import com.imran.tvmaze.utils.Constant
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Imran Khan on 12/26/2020.
 * Email : context.imran@gmail.com
 */

class ShowsRepository @Inject constructor(private val apiRepository : ApiRepository){

    fun findShows() = flow {
        emit(fetchShowsFromApi())
    }.catch {
        Log.d(TAG, "findShows: " + it.message)
    }

    private fun fetchShowsFromApi() : MutableLiveData<Shows> {
        val data = MutableLiveData<Shows>()

        apiRepository.findShows().enqueue(object : EnqueueResponse<Shows>() {
            override fun onReceived(body: Shows?, message: String?) {
                body.let {
                    data.value = body
                }
                Log.d(TAG, "onReceived: $message")
            }

            override fun onError(message: String?, code: Int) {
                Log.d(TAG, "onError: $message")
                data.value = Shows()
            }

            override fun onFailed(message: String?) {
                Log.d(TAG, "onFailed: $message")
                data.value = Shows()
            }
        })
        return data
    }

    companion object {
        private const val TAG = "JobRepository"
    }
}
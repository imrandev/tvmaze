package com.imran.tvmaze.ui.shows

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.imran.tvmaze.db.RoomManager
import com.imran.tvmaze.di.ApplicationScope
import com.imran.tvmaze.model.Shows
import com.imran.tvmaze.model.ShowsItem
import com.imran.tvmaze.network.ApiRepository
import com.imran.tvmaze.network.EnqueueResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Imran Khan on 12/26/2020.
 * Email : context.imran@gmail.com
 */

class ShowsRepository @Inject constructor(
    private val apiRepository : ApiRepository, private val roomManager: RoomManager,
    @ApplicationScope private val applicationScope: CoroutineScope
){

    fun findShows() = flow {
        emit(fetchShowsFromApi())
    }.catch {
        Log.d(TAG, "findShows: " + it.message)
    }

    private fun fetchShowsFromApi() : MutableLiveData<List<ShowsItem>> {
        val data = MutableLiveData<List<ShowsItem>>()

        apiRepository.findShows().enqueue(object : EnqueueResponse<Shows>() {
            override fun onReceived(body: Shows?, message: String?) {
                body.let {
                    // save into local database
                    body?.toList()?.let {

                        CoroutineScope(Dispatchers.Default).launch {

                            val totalSize = it.size
                            val midSize = totalSize / 2

                            val startList = it.subList(0, midSize)
                            val endList = it.subList(midSize, totalSize)

                            async(Dispatchers.IO) {
                                startList.map { item ->
                                    item.let {
                                        roomManager.showItemDao().save(item)
                                        Log.d(TAG, "startList: ")
                                    }
                                }
                            }

                            async(Dispatchers.IO) {
                                endList.map { item ->
                                    item.let {
                                        roomManager.showItemDao().save(item)
                                        Log.d(TAG, "endList: ")
                                    }
                                }
                            }

                            roomManager.showItemDao().findAll().collect { items ->
                                data.postValue(items)
                            }
                        }
                    }
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
        private const val TAG = "ShowRepository"
    }
}
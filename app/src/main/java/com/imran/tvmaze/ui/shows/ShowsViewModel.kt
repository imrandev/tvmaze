package com.imran.tvmaze.ui.shows

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.imran.tvmaze.model.Shows
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by Imran Khan on 12/26/2020.
 * Email : context.imran@gmail.com
 */

class ShowsViewModel (application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    var shows = MutableLiveData<Shows>()

    fun findShows() : MutableLiveData<Shows> {

        if (shows.value != null)
            return shows

        viewModelScope.launch {
            ShowsRepository.instance.findShows(context).collect { items ->
                run {
                    shows = items
                }
            }
        }
        return shows
    }
}
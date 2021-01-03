package com.imran.tvmaze.ui.shows

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imran.tvmaze.model.Shows
import com.imran.tvmaze.model.ShowsItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by Imran Khan on 12/26/2020.
 * Email : context.imran@gmail.com
 */

class ShowsViewModel @ViewModelInject constructor(

    private val showsRepository: ShowsRepository) : ViewModel() {

    var shows = MutableLiveData<List<ShowsItem>>()

    fun findShows() : MutableLiveData<List<ShowsItem>> {
        shows.value?.let {
            return shows
        } ?: let {
            viewModelScope.launch {
                showsRepository.findShows().collect { items ->
                    run {
                        shows = items
                    }
                }
            }
            return shows
        }
    }
}
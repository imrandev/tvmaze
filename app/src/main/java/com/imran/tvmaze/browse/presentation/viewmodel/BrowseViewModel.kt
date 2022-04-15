package com.imran.tvmaze.browse.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imran.tvmaze.browse.data.model.BrowseResponse
import com.imran.tvmaze.browse.domain.usecase.BrowseUseCase
import com.imran.tvmaze.core.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * Created by Imran Khan on 12/26/2020.
 * Email : context.imran@gmail.com
 */

@HiltViewModel
class BrowseViewModel @Inject constructor(application: Application, private val browseUseCase: BrowseUseCase) : AndroidViewModel(application) {

    private var browseTVList = MutableLiveData<State<BrowseResponse>>()

    suspend fun findShows(page: String) : LiveData<State<BrowseResponse>> {
        browseTVList.postValue(State.Loading)
        browseUseCase.getTVUseCase.execute(page).collectLatest { state ->
            when(state){
                is State.Loaded -> browseTVList.postValue(state)
                is State.Empty -> browseTVList.postValue(state)
                else -> {

                }
            }
        }
        return browseTVList
    }
}
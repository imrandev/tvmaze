package com.imran.tvmaze.browse.presentation.viewmodel

import androidx.lifecycle.*
import com.imran.tvmaze.browse.domain.usecase.BrowseUseCase
import com.imran.tvmaze.core.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Created by Imran Khan on 12/26/2020.
 * Email : context.imran@gmail.com
 */

@HiltViewModel
class BrowseViewModel @Inject constructor(private val browseUseCase: BrowseUseCase) : ViewModel() {

    fun findShows(page: String) = liveData {
        browseUseCase.getTVUseCase.execute(page).onStart {
            emit(Result.loading())
        }.collect { state ->
            emit(state)
        }
    }
}
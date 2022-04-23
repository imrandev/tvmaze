package com.imran.tvmaze.browse.presentation.viewmodel

import androidx.lifecycle.*
import com.imran.tvmaze.browse.domain.usecase.BrowseUseCase
import com.imran.tvmaze.core.base.model.Show
import com.imran.tvmaze.core.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Imran Khan on 12/26/2020.
 * Email : context.imran@gmail.com
 */

@HiltViewModel
class BrowseViewModel @Inject constructor(private val browseUseCase: BrowseUseCase) : ViewModel() {

    val tvShowList = MutableLiveData<Result<List<Show>>>()

    val isTVSearched = MutableLiveData<Boolean>()

    fun findShows(page: String): LiveData<Result<List<Show>>> {
        viewModelScope.launch {
            browseUseCase.getTVUseCase.execute(page).onStart {
                tvShowList.postValue(Result.loading())
            }.collect{
                tvShowList.postValue(it)
            }
        }
        return tvShowList
    }

    fun searchShows(query: String): LiveData<Result<List<Show>>> {
        viewModelScope.launch {
            browseUseCase.searchTVUseCase.execute(query).onStart {
                tvShowList.postValue(Result.loading())
            }.collect{
                tvShowList.postValue(it)
            }
        }
        return tvShowList
    }

    fun setTvSearch(state: Boolean){
        isTVSearched.postValue(state)
    }
}
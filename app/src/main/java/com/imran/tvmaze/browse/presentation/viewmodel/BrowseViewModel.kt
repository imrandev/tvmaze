package com.imran.tvmaze.browse.presentation.viewmodel

import androidx.lifecycle.*
import com.imran.tvmaze.browse.domain.usecase.BrowseUseCase
import com.imran.tvmaze.core.base.model.Show
import com.imran.tvmaze.core.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Imran Khan on 12/26/2020.
 * Email : context.imran@gmail.com
 */

@HiltViewModel
class BrowseViewModel @Inject constructor(private val browseUseCase: BrowseUseCase) : ViewModel() {

    val tvShowList = MutableLiveData<Result<List<Show>>>()
    var data = listOf<Show>()

    fun findShows(page: Int): LiveData<Result<List<Show>>> {

        viewModelScope.launch {
            val result = browseUseCase.getTVUseCase.invoke("$page")
                .onStart {
                    tvShowList.postValue(Result.loading())
                    delay(1000)
                }.toList().last()
            data = if (page > 0){
                data + result.data!!
            } else {
                result.data!!
            }
            tvShowList.postValue(Result.success(data))
        }
        return tvShowList
    }

    fun searchShows(query: String): LiveData<Result<List<Show>>> {
        viewModelScope.launch {
            tvShowList.value = browseUseCase.searchTVUseCase.execute(query).onStart {
                tvShowList.postValue(Result.loading())
            }.toList().last()
        }
        return tvShowList
    }
}
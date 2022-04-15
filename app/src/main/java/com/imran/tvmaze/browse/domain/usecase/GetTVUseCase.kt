package com.imran.tvmaze.browse.domain.usecase

import com.imran.tvmaze.browse.domain.repository.BrowseRepository
import com.imran.tvmaze.core.network.Result
import com.imran.tvmaze.core.utils.State
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetTVUseCase @Inject constructor(private val browseRepository: BrowseRepository) {

    fun execute(page: String) = flow {
        browseRepository.findTVByPage(page).collectLatest { result ->
            when(result){
                is Result.Success -> emit(State.Loaded(data = result.data))
                is Result.Error -> emit(State.Empty(message = result.message))
                is Result.Failure -> emit(State.Empty(message = result.message))
            }
        }
    }
}
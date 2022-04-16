package com.imran.tvmaze.browse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imran.tvmaze.browse.domain.usecase.BrowseUseCase

class BrowseViewModelFactory(private val browseUseCase: BrowseUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BrowseViewModel(browseUseCase = browseUseCase) as T
    }
}
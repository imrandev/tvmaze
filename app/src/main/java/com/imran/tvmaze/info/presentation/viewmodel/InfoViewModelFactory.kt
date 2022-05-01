package com.imran.tvmaze.info.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imran.tvmaze.info.domain.usecase.InfoUseCase

class InfoViewModelFactory(private val infoUseCase: InfoUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return InfoViewModel(infoUseCase) as T
    }
}
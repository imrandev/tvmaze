package com.imran.tvmaze.menu.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imran.tvmaze.menu.domain.usecase.GenerateMenuUseCase

class MenuViewModelFactory (private val generateMenuUseCase: GenerateMenuUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MenuViewModel(generateMenuUseCase) as T
    }
}
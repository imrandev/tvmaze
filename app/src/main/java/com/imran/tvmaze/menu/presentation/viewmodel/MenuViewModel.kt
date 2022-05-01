package com.imran.tvmaze.menu.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.imran.tvmaze.menu.domain.usecase.GenerateMenuUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.last
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(private val generateMenuUseCase: GenerateMenuUseCase)  : ViewModel() {

    fun getMenus() = liveData {
        emit(generateMenuUseCase.invoke().last())
    }
}
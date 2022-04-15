package com.imran.tvmaze.core.utils

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.imran.tvmaze.browse.domain.usecase.BrowseUseCase
import com.imran.tvmaze.browse.presentation.viewmodel.BrowseViewModel
import com.imran.tvmaze.core.base.BaseUseCase

class BrowseViewModelFactory constructor(application: Application, private val browseUseCase: BrowseUseCase) : AndroidViewModel(application) {

    fun <T : AndroidViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BrowseViewModel::class.java)) {
            BrowseViewModel(application = getApplication(), this.browseUseCase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
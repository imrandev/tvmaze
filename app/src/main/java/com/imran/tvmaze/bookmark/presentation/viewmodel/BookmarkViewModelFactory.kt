package com.imran.tvmaze.bookmark.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imran.tvmaze.bookmark.domain.usecase.FetchBookmarkUseCase

class BookmarkViewModelFactory (private val fetchBookmarkUseCase: FetchBookmarkUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookmarkViewModel(fetchBookmarkUseCase) as T
    }
}
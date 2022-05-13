package com.imran.tvmaze.bookmark.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imran.tvmaze.bookmark.domain.usecase.FetchBookmarkUseCase
import com.imran.tvmaze.core.db.domain.model.Bookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(private val fetchBookmarkUseCase: FetchBookmarkUseCase) : ViewModel() {

    public val bookmarks = MutableLiveData<List<Bookmark>>()

    fun getBookmarks() : LiveData<List<Bookmark>> {
        viewModelScope {
            val data = fetchBookmarkUseCase.invoke().collect()
        }
        return bookmarks
    }
}
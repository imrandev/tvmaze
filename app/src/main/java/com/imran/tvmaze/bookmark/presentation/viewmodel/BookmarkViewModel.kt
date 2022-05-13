package com.imran.tvmaze.bookmark.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imran.tvmaze.bookmark.domain.usecase.FetchBookmarkUseCase
import com.imran.tvmaze.core.base.model.ErrorResponse
import com.imran.tvmaze.core.db.domain.model.Bookmark
import com.imran.tvmaze.core.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(private val fetchBookmarkUseCase: FetchBookmarkUseCase) : ViewModel() {

    val bookmarks = MutableLiveData<Result<List<Bookmark>>>()

    fun getBookmarks() : LiveData<Result<List<Bookmark>>> {
        viewModelScope.launch {
            val data = fetchBookmarkUseCase.invoke().onStart {
                bookmarks.postValue(Result.loading())
            }.toList().last()
            if (data != null && data.isNotEmpty()){
                bookmarks.postValue(Result.success(data))
            } else {
                bookmarks.postValue(Result.error("No bookmark add yet! Browse TV", ErrorResponse()))
            }
        }
        return bookmarks
    }
}
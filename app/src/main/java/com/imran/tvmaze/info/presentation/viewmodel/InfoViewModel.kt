package com.imran.tvmaze.info.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imran.tvmaze.core.base.model.Show
import com.imran.tvmaze.core.db.domain.model.Bookmark
import com.imran.tvmaze.core.db.domain.model.Genre
import com.imran.tvmaze.core.network.Result
import com.imran.tvmaze.info.domain.usecase.InfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(private val infoUseCase: InfoUseCase) : ViewModel() {

    private val favoriteResult = MutableLiveData<Result<Boolean>>()

    fun insertGenre(genre: Genre) {
        infoUseCase.insertGenreUseCase.invoke(genre)
    }

    fun insertFavorite(item: Show) : LiveData<Result<Boolean>> {
        val favorite = Bookmark(
            id = item.id,
            url = item.url,
            name = item.name,
            type = item.type,
            language = item.language,
            status = item.status,
            runtime = item.runtime,
            premiered = item.premiered,
            ended = item.ended,
            genres = item.genres?.joinToString(", "),
            officialSite = item.officialSite,
            time = item.schedule?.time,
            days = item.schedule?.days?.joinToString(", "),
            rating = item.rating?.average,
            network = item.network?.name,
            tvrage = item.externals?.tvrage,
            imdb = item.externals?.imdb,
            thetvdb = item.externals?.thetvdb,
            medium = item.image?.medium,
            original = item.image?.original,
            summary = item.summary
        )
        viewModelScope.launch {
            val result = infoUseCase.insertBookmarkUseCase.invoke(favorite).last()
            favoriteResult.postValue(result)
        }
        return favoriteResult
    }


}
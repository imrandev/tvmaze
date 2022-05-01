package com.imran.tvmaze.core.db.domain.usecase

import android.util.Log
import com.imran.tvmaze.core.base.model.ErrorResponse
import com.imran.tvmaze.core.db.domain.model.Genre
import com.imran.tvmaze.core.db.domain.repository.GenreRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import com.imran.tvmaze.core.network.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class InsertGenreUseCase @Inject constructor(private val genreRepository: GenreRepository) {

    private val TAG = "InsertGenreUseCase"

    fun invoke(genre: Genre) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                var data = genreRepository.findByName(genre.name)
                if (data != null){
                    genre.id = data.id
                    genre.priority = data.priority
                    genre.points = data.points + genre.points
                }
                data = genreRepository.insert(genre)
                when {
                    data == null -> {
                        Log.d(TAG, "invoke: Unable to insert")
                    }
                    genre.id > 0 -> {
                        Log.d(TAG, "invoke: Successfully genre updated")
                    }
                    else -> {
                        Log.d(TAG, "invoke: Successfully added new genre")
                    }
                }
            }
        } catch (ex: Exception){
            Log.e(TAG, "invoke: " + ex.message)
        }
    }
}
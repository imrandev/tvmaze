package com.imran.tvmaze.core.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imran.tvmaze.app.TVMazeApp
import com.imran.tvmaze.browse.domain.usecase.BrowseUseCase
import com.imran.tvmaze.browse.presentation.viewmodel.BrowseViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    @ActivityScoped
    fun provideBrowseViewModel (@ApplicationContext application: Application, browseUseCase: BrowseUseCase) : BrowseViewModel {
        val factory = object : ViewModelProvider.AndroidViewModelFactory(application) {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BrowseViewModel(application, browseUseCase = browseUseCase) as T
            }
        }
        return  factory.create(BrowseViewModel::class.java)
    }
}
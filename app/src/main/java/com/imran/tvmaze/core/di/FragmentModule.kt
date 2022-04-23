package com.imran.tvmaze.core.di

import com.imran.tvmaze.browse.domain.usecase.BrowseUseCase
import com.imran.tvmaze.browse.presentation.viewmodel.BrowseViewModel
import com.imran.tvmaze.browse.presentation.viewmodel.BrowseViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class FragmentModule {

    @Provides
    @FragmentScoped
    fun provideBrowseViewModel (browseUseCase: BrowseUseCase) : BrowseViewModel {
        return BrowseViewModelFactory(browseUseCase = browseUseCase).create(BrowseViewModel::class.java)
    }
}
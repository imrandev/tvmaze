package com.imran.tvmaze.core.di

import com.imran.tvmaze.browse.domain.usecase.BrowseUseCase
import com.imran.tvmaze.browse.presentation.viewmodel.BrowseViewModel
import com.imran.tvmaze.browse.presentation.viewmodel.BrowseViewModelFactory
import com.imran.tvmaze.info.domain.usecase.InfoUseCase
import com.imran.tvmaze.info.presentation.viewmodel.InfoViewModel
import com.imran.tvmaze.info.presentation.viewmodel.InfoViewModelFactory
import com.imran.tvmaze.menu.domain.usecase.GenerateMenuUseCase
import com.imran.tvmaze.menu.presentation.viewmodel.MenuViewModel
import com.imran.tvmaze.menu.presentation.viewmodel.MenuViewModelFactory
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

    @Provides
    @FragmentScoped
    fun provideInfoViewModel (infoUseCase: InfoUseCase) : InfoViewModel {
        return InfoViewModelFactory(infoUseCase).create(InfoViewModel::class.java)
    }

    @Provides
    @FragmentScoped
    fun provideMenuViewModel (generateMenuUseCase: GenerateMenuUseCase) : MenuViewModel {
        return MenuViewModelFactory(generateMenuUseCase).create(MenuViewModel::class.java)
    }
}
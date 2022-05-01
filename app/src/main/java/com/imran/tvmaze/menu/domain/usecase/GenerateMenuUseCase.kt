package com.imran.tvmaze.menu.domain.usecase

import com.imran.tvmaze.R
import com.imran.tvmaze.menu.domain.model.MenuItem
import kotlinx.coroutines.flow.flow

class GenerateMenuUseCase {

    private val icons = listOf(R.drawable.ic_bookmarks_amber_700_24dp, R.drawable.ic_settings_amber_700_24dp)
    private val labels = listOf("Bookmarks", "Settings")
    private val subLabels = listOf("See all of your saved bookmarks", "Set preferences like 'Favorite Genre' etc")

    fun invoke() = flow {
        val menus = mutableListOf<MenuItem>()
        icons.forEachIndexed { index, icon ->
            menus.add(MenuItem(id = index, icon = icon, label = labels[index], subLabel = subLabels[index]))
        }
        emit(menus)
    }
}
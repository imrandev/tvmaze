package com.imran.tvmaze.menu.domain.model

import androidx.annotation.IdRes
import com.imran.tvmaze.core.base.model.Core

data class MenuItem (
    var label: String,
    var subLabel: String,
    @IdRes
    var icon: Int?,
    override var id: Int
) : Core()
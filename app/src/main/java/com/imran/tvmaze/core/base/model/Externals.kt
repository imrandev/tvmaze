package com.imran.tvmaze.core.base.model

import java.io.Serializable

data class Externals(
    val imdb: String?,
    val thetvdb: Int?,
    val tvrage: Int?
) : Serializable
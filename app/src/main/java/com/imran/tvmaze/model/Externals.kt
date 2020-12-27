package com.imran.tvmaze.model

import java.io.Serializable

data class Externals(
    val imdb: Any,
    val thetvdb: Any,
    val tvrage: Int
) : Serializable
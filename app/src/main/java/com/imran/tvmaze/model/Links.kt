package com.imran.tvmaze.model

import java.io.Serializable

data class Links(
    val previousepisode: Previousepisode,
    val self: Self
) : Serializable
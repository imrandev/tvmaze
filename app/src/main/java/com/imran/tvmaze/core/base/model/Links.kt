package com.imran.tvmaze.core.base.model

import java.io.Serializable

data class Links(
    val previousepisode: Previousepisode,
    val self: Self,
    val nextepisode: Nextepisode
) : Serializable
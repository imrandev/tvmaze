package com.imran.tvmaze.core.base.model

import java.io.Serializable

data class WebChannel(
    val country: CountryX?,
    val id: Int?,
    val name: String?
) : Serializable
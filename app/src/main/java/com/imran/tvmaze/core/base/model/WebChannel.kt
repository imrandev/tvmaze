package com.imran.tvmaze.core.base.model

import com.imran.tvmaze.core.base.model.CountryX
import java.io.Serializable

data class WebChannel(
    val country: CountryX,
    val id: Int,
    val name: String
) : Serializable
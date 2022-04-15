package com.imran.tvmaze.core.base.model

import java.io.Serializable

data class Country(
    val code: String,
    val name: String,
    val timezone: String
) : Serializable
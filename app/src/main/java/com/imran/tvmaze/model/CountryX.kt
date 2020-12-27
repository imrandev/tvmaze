package com.imran.tvmaze.model

import java.io.Serializable

data class CountryX(
    val code: String,
    val name: String,
    val timezone: String
) : Serializable
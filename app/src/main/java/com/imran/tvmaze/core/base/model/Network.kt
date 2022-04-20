package com.imran.tvmaze.core.base.model

import java.io.Serializable

data class Network(
    val country: Country,
    val id: Int,
    val name: String,
    val officialSite: String
) : Serializable
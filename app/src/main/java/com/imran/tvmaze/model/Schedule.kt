package com.imran.tvmaze.model

import java.io.Serializable

data class Schedule(
    val days: List<String>,
    val time: String
) : Serializable
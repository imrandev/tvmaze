package com.imran.tvmaze.core.base.model

import java.io.Serializable

data class Image(
    val medium: String?,
    val original: String?
) : Serializable
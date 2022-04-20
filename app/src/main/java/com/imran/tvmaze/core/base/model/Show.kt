package com.imran.tvmaze.core.base.model

import java.io.Serializable

data class Show(
    val _links: Links,
    val externals: Externals?,
    val genres: List<String>?,
    override var id: Int,
    val image: Image?,
    val language: String?,
    val name: String,
    val network: Network?,
    val ended: String?,
    val officialSite: String?,
    val premiered: String?,
    val rating: Rating,
    val runtime: Int?,
    val averageRuntime: Int?,
    val schedule: Schedule?,
    val status: String?,
    val summary: String?,
    val type: String?,
    val updated: Int?,
    val url: String?,
    val webChannel: WebChannel?,
    val weight: Int?
) : Core(), Serializable
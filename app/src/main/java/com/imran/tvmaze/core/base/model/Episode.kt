package com.imran.tvmaze.core.base.model

data class Episode(
    val id: Int,
    val url: String,
    val name: String,
    val season: Int,
    val number: Int,
    val type: String,
    val airdate: String,
    val airtime: String,
    val airstamp: String,
    val runtime: Int,
    val rating: Rating,
    val image: Image,
    val summary: String,
    val _links: Links,
    val score: Float,
    val show: Show
)

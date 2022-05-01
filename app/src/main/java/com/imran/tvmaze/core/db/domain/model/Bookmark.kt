package com.imran.tvmaze.core.db.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bookmark(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo
    val url: String?,
    @ColumnInfo
    val name: String?,
    @ColumnInfo
    val type: String?,
    @ColumnInfo
    val language: String?,
    @ColumnInfo
    val status: String?,
    @ColumnInfo
    val runtime: Int?,
    @ColumnInfo
    val premiered: String?,
    @ColumnInfo
    val ended: String?,
    @ColumnInfo
    val genres: String?,
    @ColumnInfo
    val officialSite: String?,
    @ColumnInfo
    val time: String?,
    @ColumnInfo
    val days: String?,
    @ColumnInfo
    val rating: Double?,
    @ColumnInfo
    val network: String?,
    @ColumnInfo
    val tvrage: Int?,
    @ColumnInfo
    val imdb: String?,
    @ColumnInfo
    val thetvdb: Int?,
    @ColumnInfo
    val medium: String?,
    @ColumnInfo
    val original: String?,
    @ColumnInfo
    val summary: String?
)
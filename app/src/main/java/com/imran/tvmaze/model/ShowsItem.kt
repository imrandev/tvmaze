package com.imran.tvmaze.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class ShowsItem (
    @ColumnInfo val _links: Links,
    @ColumnInfo val externals: Externals?,
    @ColumnInfo val genres: List<String> = ArrayList(),
    @PrimaryKey val id: Int,
    @ColumnInfo val image: Image,
    @ColumnInfo val language: String,
    @ColumnInfo val name: String,
    @ColumnInfo val network: Network?,
    @ColumnInfo val officialSite: String?,
    @ColumnInfo val premiered: String,
    @ColumnInfo val rating: Rating?,
    @ColumnInfo val runtime: Int,
    @ColumnInfo val schedule: Schedule?,
    @ColumnInfo val status: String,
    @ColumnInfo val summary: String,
    @ColumnInfo val type: String,
    @ColumnInfo val updated: Int,
    @ColumnInfo val url: String,
    @ColumnInfo val webChannel: WebChannel?,
    @ColumnInfo val weight: Int
) : Serializable
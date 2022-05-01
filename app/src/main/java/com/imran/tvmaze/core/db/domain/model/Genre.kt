package com.imran.tvmaze.core.db.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Genre(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    var priority: Int,
    @ColumnInfo
    var points: Float
)

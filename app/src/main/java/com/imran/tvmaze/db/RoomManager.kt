package com.imran.tvmaze.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.imran.tvmaze.model.ShowsItem

/**
 * Created by Imran Khan on 11/29/2020.
 * Email : context.imran@gmail.com
 */

@Database(
        entities = [ShowsItem::class],
        version = 1
)
@TypeConverters(
    GenreTypeConverter::class, LinksTypeConverter::class,
    ExternalsTypeConverter::class, ImageTypeConverter::class,
    NetworkTypeConverter::class, RatingTypeConverter::class,
    ScheduleTypeConverter::class, WebChannelTypeConverter::class
)
abstract class RoomManager : RoomDatabase(){
    abstract fun showItemDao(): ShowItemDao
}
package com.imran.tvmaze.db

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import com.imran.tvmaze.model.*
import java.lang.reflect.Type

/**
 * Created by Imran Khan on 1/2/2021.
 * Email : context.imran@gmail.com
 */

class GenreTypeConverter {

    @TypeConverter
    fun fromString(value: String?): List<String?>? {

        if (value.equals("null")) return ArrayList()

        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}

class LinksTypeConverter {

    @TypeConverter
    fun fromString(value: String?): Links? {

        if (value.equals("null")) return null

        val type: Type = object : TypeToken<Links?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromObject(obj: Links?): String? {
        val gson = Gson()
        return gson.toJson(obj)
    }
}

class ExternalsTypeConverter {

    @TypeConverter
    fun fromString(value: String?): Externals? {

        if (value.equals("null")) return null

        val type: Type = object : TypeToken<Externals?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromObject(obj: Externals?): String? {
        val gson = Gson()
        return gson.toJson(obj)
    }
}

class ImageTypeConverter {

    @TypeConverter
    fun fromString(value: String?): Image? {

        if (value.equals("null")) return null

        val type: Type = object : TypeToken<Image?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromObject(obj: Image?): String? {
        val gson = Gson()
        return gson.toJson(obj)
    }
}

class NetworkTypeConverter {

    @TypeConverter
    fun fromString(value: String?): Network? {

        if (value.equals("null")) return null

        val type: Type = object : TypeToken<Network?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromObject(obj: Network?): String? {
        val gson = Gson()
        return gson.toJson(obj)
    }
}

class RatingTypeConverter {

    @TypeConverter
    fun fromString(value: String?): Rating? {

        if (value.equals("null")) return null

        val type: Type = object : TypeToken<Rating?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromObject(obj: Rating?): String? {
        val gson = Gson()
        return gson.toJson(obj)
    }
}

class ScheduleTypeConverter {

    @TypeConverter
    fun fromString(value: String?): Schedule? {

        if (value.equals("null")) return null

        val type: Type = object : TypeToken<Schedule?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromObject(obj: Schedule?): String? {
        val gson = Gson()
        return gson.toJson(obj)
    }
}

class WebChannelTypeConverter {

    @TypeConverter
    fun fromString(value: String?): WebChannel? {

        if (value.equals("null")) return null

        val type: Type = object : TypeToken<WebChannel?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromObject(obj: WebChannel?): String? {
        val gson = Gson()
        return gson.toJson(obj)
    }
}
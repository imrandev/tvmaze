package com.imran.tvmaze.core.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Imran Khan on 12/26/2020.
 * Email : context.imran@gmail.com
 */

object ViewType {
    const val EMPTY_VIEW = -1
    const val LOADER_VIEW = 0
    const val NORMAL_VIEW = 1
}

object DateUtil {

    @Throws(ParseException::class)
    fun getFormattedDate(actualDate: String) : String{
        if (actualDate.isBlank()) return ""

        val formatted = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = sdf.parse(actualDate)
        return formatted.format(date!!)
    }

    @Throws(ParseException::class)
    fun getFormattedTime(actualDate: String) : String{

        if (actualDate.isBlank()) return ""

        val formatted = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = sdf.parse(actualDate)
        return formatted.format(date!!)
    }
}
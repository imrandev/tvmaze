package com.imran.tvmaze.core.utils


/**
 * Created by Imran Khan on 12/26/2020.
 * Email : context.imran@gmail.com
 */

object Constant {

    const val DB_NAME: String = "tvmaze-db"
    const val BASE_URL = "http://api.tvmaze.com"

    const val DEFAULT_GET_ERROR_MESSAGE = "Unable to fetch data"
    const val cacheSize = (5 * 1024 * 1024).toLong()
}

object Priority {
    const val high = 2
    const val normal = 1
    const val low = 0
}

object Message {
    const val BOOKMARK_ADDED = "Added in bookmark!"
    const val BOOKMARK_REMOVED = "Removed from bookmark!"
    const val ERROR_DEFAULT = "Something went wrong, please try again!"
}
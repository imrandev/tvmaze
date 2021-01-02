package com.imran.tvmaze.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Imran Khan on 1/2/2021.
 * Email : context.imran@gmail.com
 */

@HiltAndroidApp
class TVMaze : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
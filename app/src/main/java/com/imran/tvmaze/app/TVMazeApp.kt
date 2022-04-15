package com.imran.tvmaze.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TVMazeApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
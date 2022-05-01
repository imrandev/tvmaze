package com.imran.tvmaze.app

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TVMazeApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
    }
}
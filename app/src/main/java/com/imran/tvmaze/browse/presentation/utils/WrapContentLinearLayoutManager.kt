package com.imran.tvmaze.browse.presentation.utils

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WrapContentLinearLayoutManager (context: Context) : LinearLayoutManager(context) {

    private val TAG = "WrapContentLinearLayout"

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (ex: IndexOutOfBoundsException){
            Log.e(TAG, "onLayoutChildren: " + ex.message)
        }
    }
}
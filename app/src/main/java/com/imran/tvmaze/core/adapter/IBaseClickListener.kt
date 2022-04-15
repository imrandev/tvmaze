package com.imran.tvmaze.core.adapter

import android.view.View

interface IBaseClickListener<T> {
    fun onItemClicked(view: View?, item: T, position: Int)
}
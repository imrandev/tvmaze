package com.imran.tvmaze.core.callback

import com.imran.tvmaze.core.utils.State

interface StateCallback<R> {
    fun onState(state: State<R>);
}
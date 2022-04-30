package com.imran.tvmaze.core.adapter

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerPagination(
    private val onLoadMore: (page: Int, scrollPosition: Int) -> Unit,
    private val scrollToTop: (visibility: Boolean) -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val itemCount = recyclerView.adapter?.itemCount ?: 0
        if (itemCount < THRESHOLD) return
        val page = itemCount.div(THRESHOLD)
        val visibleItemPosition = recyclerView.layoutManager.run {
            when(this){
                is LinearLayoutManager -> this.findLastCompletelyVisibleItemPosition()
                else -> 0
            }
        }
        Log.d(TAG, "onScrolled: Visible Item Position $visibleItemPosition")
        if (visibleItemPosition == itemCount - 1 && !recyclerView.canScrollVertically(1) && dy > 0) {
            onLoadMore(page, itemCount)
        }
        scrollToTop(!recyclerView.canScrollVertically(-1))
        Log.d(TAG, String.format("onScrolled: dx = %s dy %s", dx, dy))
    }

    companion object {
        private const val TAG = "RecyclerPagination"
        private const val THRESHOLD = 240
    }
}
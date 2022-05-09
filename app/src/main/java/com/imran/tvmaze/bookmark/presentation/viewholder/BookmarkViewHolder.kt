package com.imran.tvmaze.bookmark.presentation.viewholder

import androidx.databinding.ViewDataBinding
import com.imran.tvmaze.core.adapter.BaseViewHolder
import com.imran.tvmaze.core.adapter.IBaseClickListener
import com.imran.tvmaze.core.db.domain.model.Bookmark

class BookmarkViewHolder (viewDataBinding: ViewDataBinding) : BaseViewHolder<Bookmark, IBaseClickListener<Bookmark>>(viewDataBinding) {

    override fun onEmptyBind() {
        TODO("Not yet implemented")
    }

    override fun onLoadedBind(item: Bookmark) {
        TODO("Not yet implemented")
    }

    override fun onLoadedBind(item: Bookmark, onItemClickedListener: IBaseClickListener<Bookmark>) {
        TODO("Not yet implemented")
    }

    override fun onLoadingBind() {
        TODO("Not yet implemented")
    }

    override fun onInfiniteLoadingBind() {
        TODO("Not yet implemented")
    }

    override fun onViewRecycled() {
        TODO("Not yet implemented")
    }
}
package com.imran.tvmaze.core.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class BaseViewHolder<T, L : IBaseClickListener<T>?>(
    viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

    abstract fun onEmptyBind()
    abstract fun onLoadedBind(item: T)
    abstract fun onLoadedBind(item: T, onItemClickedListener: L)
    abstract fun onLoadingBind()
    abstract fun onInfiniteLoadingBind()
    abstract fun onViewRecycled()

    fun setParentViewListener(onItemClickListener: L, item: T) {
        itemView.setOnClickListener { view: View? ->
            onItemClickListener!!.onItemClicked(
                view,
                item,
                absoluteAdapterPosition
            )
        }
    }

    protected fun setChildViewListener(
        view: View,
        onItemClickListener: L,
        item: T
    ) {
        view.setOnClickListener { view1 ->
            onItemClickListener!!.onItemClicked(
                view1,
                item,
                absoluteAdapterPosition
            )
        }
    }
}
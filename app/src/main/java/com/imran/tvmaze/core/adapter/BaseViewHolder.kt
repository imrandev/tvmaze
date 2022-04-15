package com.imran.tvmaze.core.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class BaseViewHolder<T, L : IBaseClickListener<T>?>(viewDataBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {

    abstract fun onBindView(isEmpty: Boolean)
    abstract fun onBindView(`object`: T)
    abstract fun onBindView(`object`: T, onItemClickedListener: L)
    abstract fun onViewRecycled()

    fun attachListener(onItemClickListener: L, item: T) {
        itemView.setOnClickListener { view: View? ->
            onItemClickListener!!.onItemClicked(
                view,
                item,
                adapterPosition
            )
        }
    }

    protected fun attachListenerWithCustomView(
        view: View,
        onItemClickListener: L,
        item: T
    ) {
        view.setOnClickListener { view1 ->
            onItemClickListener!!.onItemClicked(
                view1,
                item,
                adapterPosition
            )
        }
    }
}
package com.imran.tvmaze.core.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.imran.tvmaze.core.base.model.Core
import com.imran.tvmaze.core.utils.ViewType

abstract class BaseRecyclerAdapter<T : Core, L : IBaseClickListener<T>?> protected constructor(
    private var itemList: MutableList<T> = mutableListOf(),
    private var onItemClickedListener : L
) : RecyclerView.Adapter<BaseViewHolder<T, L>>() {

    private var onItemClickListener: L = this.onItemClickedListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T, L> {
        return if (itemList.size == 0) onCreateEmptyView(parent, viewType) else onCreateView(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T, L>, position: Int) {
        if (itemList.size > 0 && getItemViewType(position) == ViewType.NORMAL_VIEW){
            val item = itemList[position]
            holder.onBindView(item, onItemClickListener)
        } else if (itemList.isEmpty()){
            holder.onBindView(true)
        } else {
            holder.onBindView(false)
        }
    }

    override fun getItemCount(): Int {
        return if (itemList.size == 0) 1 else itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList.size) {
            0 -> ViewType.EMPTY_VIEW
            else -> ViewType.NORMAL_VIEW
        }
    }

    fun update(newItemList: List<T>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilItemCallback(itemList, newItemList))
        itemList.addAll(newItemList)
        if (newItemList.isNotEmpty()){
            val start = if (itemList.isEmpty()) 0 else itemList.size - 1
            notifyItemRangeChanged(start, newItemList.size)
        }
        diffResult.dispatchUpdatesTo(this)
    }

    fun add(newItemList: List<T>?) {
        val startPosition = itemCount
        if (newItemList != null) {
            itemList.clear()
            itemList.addAll(newItemList)
        }
        notifyItemRangeInserted(startPosition, itemList.size)
    }

    fun remove(item: T) {
        val position = itemList.indexOf(item)
        if (itemList.remove(item)) notifyItemRemoved(position)
    }

    abstract fun onCreateView(parent: ViewGroup?, viewType: Int): BaseViewHolder<T, L>
    abstract fun onCreateEmptyView(parent: ViewGroup?, viewType: Int): BaseViewHolder<T, L>
}
package com.imran.tvmaze.core.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.imran.tvmaze.core.base.model.Core

abstract class BaseRecyclerAdapter<T : Core, L : IBaseClickListener<T>?> protected constructor(
    private var itemList: MutableList<T> = mutableListOf(),
    private var onItemClickedListener : L
) : RecyclerView.Adapter<BaseViewHolder<T, L>>() {

    private var onItemClickListener: L = this.onItemClickedListener

    companion object {
        const val EMPTY = 0
        const val LOADING = 1
        const val LOADED = 2
        const val INFINITE = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when(viewType){
        EMPTY -> onEmptyView(parent)
        LOADING -> onLoadingView(parent)
        LOADED -> onLoadedView(parent)
        else -> onInfiniteLoadingView(parent)
    }


    override fun onBindViewHolder(holder: BaseViewHolder<T, L>, position: Int) {
        when(getItemViewType(position)){
            EMPTY -> {
                holder.onEmptyBind()
            }
            LOADING -> {
                holder.onLoadingBind()
            }
            LOADED -> {
                val item = itemList[position]
                if (onItemClickListener != null){
                    holder.onLoadedBind(item, onItemClickedListener)
                } else {
                    holder.onLoadedBind(item)
                }
            }
            INFINITE -> {
                holder.onInfiniteLoadingBind()
            }
        }
    }

    override fun getItemCount(): Int {
        return if (itemList.size == 0) 1 else itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            itemList.size == 0 -> EMPTY
            itemList.size > 0 -> LOADED
            position % 240 == 0 -> INFINITE
            else -> LOADING
        }
    }

    fun update(newItemList: List<T>) {
        if (newItemList.isEmpty()) return
        val diffResult = DiffUtil.calculateDiff(DiffUtilItemCallback(itemList, newItemList))
        itemList = newItemList.toMutableList()
        notifyItemRangeChanged(0, newItemList.size)
        diffResult.dispatchUpdatesTo(this)
    }

    fun add(newItemList: List<T>) {
        if (newItemList.isEmpty()) return
        val diffResult = DiffUtil.calculateDiff(DiffUtilItemCallback(itemList, newItemList))
        itemList.addAll(newItemList)
        val start = if (itemList.isEmpty()) 0 else itemList.size
        notifyItemRangeInserted(start, newItemList.size)
        diffResult.dispatchUpdatesTo(this)
    }

    fun add(newItemList: List<T>, callback: (
        startPosition: Int, itemCount: Int, diffResult: DiffUtil.DiffResult,
        adapter: RecyclerView.Adapter<BaseViewHolder<T, L>>)-> Unit
    ) {
        if (newItemList.isEmpty()) return
        if (itemList.size > 0 && itemList[0].id == newItemList[0].id)
            itemList.clear()

        val diffResult = DiffUtil.calculateDiff(DiffUtilItemCallback(itemList, newItemList))
        itemList = (itemList + newItemList.toMutableList()) as MutableList<T>

        val start = if (itemList.isEmpty()) 0 else itemList.size
        callback(start, newItemList.size, diffResult, this)
    }

    fun remove(item: T) {
        val position = itemList.indexOf(item)
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItem(position: Int) = itemList[position]

    fun add(position: Int, model: T) {
        itemList.add(position, model)
        notifyItemChanged(position)
    }

    abstract fun onLoadedView(parent: ViewGroup): BaseViewHolder<T, L>

    abstract fun onLoadingView(parent: ViewGroup): BaseViewHolder<T, L>

    abstract fun onInfiniteLoadingView(parent: ViewGroup): BaseViewHolder<T, L>

    abstract fun onEmptyView(parent: ViewGroup): BaseViewHolder<T, L>
}
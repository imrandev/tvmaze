package com.imran.tvmaze.core.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.imran.tvmaze.core.utils.ViewType

abstract class BaseRecyclerAdapter<T, L : IBaseClickListener<T>?> protected constructor(
    private var itemList: MutableList<T>?,
    private var onItemClickedListener : L
) : RecyclerView.Adapter<BaseViewHolder<T, L>>() {

    private var onItemClickListener: L = this.onItemClickedListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T, L> {
        return if (itemList == null || itemList!!.size == 0)
            onCreateEmptyView(parent, viewType) else onCreateView(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T, L>, position: Int) {
        if (itemList != null && itemList!!.size > 0 && getItemViewType(position) == ViewType.NORMAL_VIEW){
            val item = itemList!!.get(position)
            holder.onBindView(item, onItemClickListener)
        } else if (itemList != null && itemList!!.isEmpty()){
            holder.onBindView(true)
        } else {
            holder.onBindView(false)
        }
    }

    override fun getItemCount(): Int {
        return if (itemList == null || itemList!!.size == 0) 1 else itemList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            itemList == null -> ViewType.LOADER_VIEW
            itemList != null && itemList!!.size == 0 -> ViewType.EMPTY_VIEW
            else -> ViewType.NORMAL_VIEW
        }
    }

    fun update(newItemList: List<T>) {
        val diffResult =
            itemList?.let {
                DiffUtil.calculateDiff(object : DiffUtilItemCallback<T>(it, newItemList){
                    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                        return areSameItems(itemList?.get(oldItemPosition), itemList?.get(newItemPosition))
                    }

                    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                        return areSameContents(itemList?.get(oldItemPosition), itemList?.get(newItemPosition))
                    }
                })
            }

        if (itemList == null){
            this.itemList = ArrayList()
        }

        if (newItemList.isNotEmpty()){
            this.itemList?.clear()
            this.itemList?.addAll(newItemList)
        }

        notifyDataSetChanged()
        diffResult?.dispatchUpdatesTo(this)
    }

    fun add(newItemList: List<T>?) {
        val startPosition = itemCount
        if (newItemList != null) {
            itemList!!.clear()
            itemList!!.addAll(newItemList)
        }
        notifyItemRangeInserted(startPosition, itemList!!.size)
    }

    fun remove(item: T) {
        val position = itemList!!.indexOf(item)
        if (itemList!!.remove(item)) notifyItemRemoved(position)
    }

    abstract fun onCreateView(parent: ViewGroup?, viewType: Int): BaseViewHolder<T, L>
    abstract fun onCreateEmptyView(parent: ViewGroup?, viewType: Int): BaseViewHolder<T, L>
    abstract fun areSameItems(oldItem: T?, newItem: T?): Boolean
    abstract fun areSameContents(oldItem: T?, newItem: T?): Boolean
}
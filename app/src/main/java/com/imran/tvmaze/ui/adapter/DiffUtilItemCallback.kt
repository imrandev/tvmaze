package com.imran.tvmaze.ui.adapter

import androidx.recyclerview.widget.DiffUtil

open class DiffUtilItemCallback<T>(private val oldList: MutableList<T>, private val newList : MutableList<T>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }
}
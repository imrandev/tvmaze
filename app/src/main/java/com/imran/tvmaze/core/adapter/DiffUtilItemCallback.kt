package com.imran.tvmaze.core.adapter

import androidx.recyclerview.widget.DiffUtil
import com.imran.tvmaze.core.base.model.Core

open class DiffUtilItemCallback<T : Core>(private val oldList: List<T>, private val newList : List<T>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition] == newList[newItemPosition] -> true
            oldList[oldItemPosition].id == newList[newItemPosition].id -> true
            else -> false
        }
    }
}
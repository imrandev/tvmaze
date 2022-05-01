package com.imran.tvmaze.menu.presentation.viewholder

import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.imran.tvmaze.R
import com.imran.tvmaze.core.adapter.BaseViewHolder
import com.imran.tvmaze.core.adapter.IBaseClickListener
import com.imran.tvmaze.databinding.RvItemMenuBinding
import com.imran.tvmaze.menu.domain.model.MenuItem

class MenuViewHolder (private val viewDataBinding: ViewDataBinding) : BaseViewHolder<MenuItem, IBaseClickListener<MenuItem>>(viewDataBinding) {

    override fun onEmptyBind() {
        TODO("Not yet implemented")
    }

    override fun onLoadedBind(item: MenuItem) {
        TODO("Not yet implemented")
    }

    override fun onLoadedBind(item: MenuItem, onItemClickedListener: IBaseClickListener<MenuItem>) {
        val itemMenuBinding = viewDataBinding as RvItemMenuBinding

        Glide.with(itemView)
            .load(ContextCompat.getDrawable(itemView.context, item.icon!!))
            .override(
                itemMenuBinding.rvItemMenuIcon.measuredWidth,
                itemMenuBinding.rvItemMenuIcon.measuredHeight
            )
            .error(R.drawable.ic_local_movies_green_600_24dp)
            .into(itemMenuBinding.rvItemMenuIcon)

        itemMenuBinding.rvItemMenuLabel.text = item.label
        itemMenuBinding.rvItemMenuSubLabel.text = item.subLabel

        setParentViewListener(onItemClickedListener, item)
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
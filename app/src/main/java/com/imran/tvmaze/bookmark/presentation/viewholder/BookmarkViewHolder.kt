package com.imran.tvmaze.bookmark.presentation.viewholder

import android.graphics.drawable.Drawable
import android.view.View
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.imran.tvmaze.R
import com.imran.tvmaze.core.adapter.BaseViewHolder
import com.imran.tvmaze.core.adapter.IBaseClickListener
import com.imran.tvmaze.core.db.domain.model.Bookmark
import com.imran.tvmaze.databinding.RvItemBrowseBinding

class BookmarkViewHolder(private val viewDataBinding: ViewDataBinding) : BaseViewHolder<Bookmark, IBaseClickListener<Bookmark>>(viewDataBinding) {

    override fun onEmptyBind() {

    }

    override fun onLoadedBind(item: Bookmark) {

    }

    override fun onLoadedBind(item: Bookmark, onItemClickedListener: IBaseClickListener<Bookmark>) {
        val itemShowsBinding = viewDataBinding as RvItemBrowseBinding
        itemShowsBinding.rvItemFavoriteStatus.visibility = View.GONE

        Glide.with(itemView)
            .load(item.url)
            .override(
                itemShowsBinding.rvItemShowsPoster.measuredWidth,
                itemShowsBinding.rvItemShowsPoster.measuredHeight
            )
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    itemShowsBinding.itemProgressBar.visibility = View.GONE
                    return false
                }
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    itemShowsBinding.itemProgressBar.visibility = View.GONE
                    return false
                }
            })
            .error(R.drawable.ic_local_movies_green_600_24dp)
            .into(itemShowsBinding.rvItemShowsPoster)

        itemShowsBinding.rvItemShowsName.text = item.name
        val avgRating = (item.rating?.div(10))?.times(5)
        if (avgRating != null) {
            itemShowsBinding.rvItemShowsRating.rating = avgRating.toFloat()
        }
        itemShowsBinding.rvItemShowsStatus.text = item.status
        itemShowsBinding.rvItemShowsGenres.text = item.genres

        //setChildViewListener(itemShowsBinding.rvItemForwardToDetails , onItemClickedListener, item)
    }

    override fun onLoadingBind() {

    }

    override fun onInfiniteLoadingBind() {

    }

    override fun onViewRecycled() {

    }
}
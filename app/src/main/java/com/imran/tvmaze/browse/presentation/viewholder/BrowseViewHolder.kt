package com.imran.tvmaze.browse.presentation.viewholder

import android.graphics.drawable.Drawable
import android.view.View
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.imran.tvmaze.R
import com.imran.tvmaze.core.base.model.Show
import com.imran.tvmaze.core.adapter.BaseViewHolder
import com.imran.tvmaze.core.adapter.IBaseClickListener
import com.imran.tvmaze.databinding.RvItemBrowseBinding

/**
 * Created by Imran Khan on 12/26/2020.
 * Email : context.imran@gmail.com
 */

class BrowseViewHolder (private val viewDataBinding: ViewDataBinding) : BaseViewHolder<Show, IBaseClickListener<Show>>(viewDataBinding) {

    override fun onViewRecycled() {

    }

    override fun onEmptyBind() {

    }

    override fun onLoadedBind(item: Show) {
        TODO("Not yet implemented")
    }

    override fun onLoadedBind(item: Show, onItemClickedListener: IBaseClickListener<Show>) {
        val itemShowsBinding = viewDataBinding as RvItemBrowseBinding
        val mediumImage = if (item.image != null) item.image.medium else null
        Glide.with(itemView)
            .load(mediumImage)
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
            .error(R.drawable.baseline_local_movies_green_600_24dp)
            .into(itemShowsBinding.rvItemShowsPoster)

        itemShowsBinding.rvItemShowsName.text = item.name

        val avgRating = (item.rating?.average?.div(10))?.times(5)
        if (avgRating != null) {
            itemShowsBinding.rvItemShowsRating.rating = avgRating.toFloat()
        }
        itemShowsBinding.rvItemShowsStatus.text = item.status
        val genres = item.genres?.joinToString(", ")
        itemShowsBinding.rvItemShowsGenres.text = genres

        setChildViewListener(itemShowsBinding.rvItemForwardToDetails , onItemClickedListener, item)
        //attachListenerWithCustomView(itemShowsBinding.rvItemClear , onItemClickedListener, item)
    }

    override fun onLoadingBind() {
        TODO("Not yet implemented")
    }

    override fun onInfiniteLoadingBind() {
        TODO("Not yet implemented")
    }
}
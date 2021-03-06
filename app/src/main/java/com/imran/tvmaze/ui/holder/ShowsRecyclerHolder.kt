package com.imran.tvmaze.ui.holder

import android.graphics.drawable.Drawable
import android.view.View
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.imran.tvmaze.R
import com.imran.tvmaze.databinding.RvItemEmptyBinding
import com.imran.tvmaze.databinding.RvItemShowsBinding
import com.imran.tvmaze.model.ShowsItem
import com.imran.tvmaze.ui.adapter.BaseViewHolder
import com.imran.tvmaze.ui.adapter.IBaseClickListener

/**
 * Created by Imran Khan on 12/26/2020.
 * Email : context.imran@gmail.com
 */

class ShowsRecyclerHolder (private val viewDataBinding: ViewDataBinding) : BaseViewHolder<ShowsItem, IBaseClickListener<ShowsItem>>(viewDataBinding) {

    override fun onBindView(isEmpty : Boolean) {
        val itemShowsBinding = viewDataBinding as RvItemEmptyBinding
        itemShowsBinding.rvItemProgress.visibility = if (isEmpty) View.GONE else View.VISIBLE
        itemShowsBinding.rvNoInternetAlert.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    override fun onBindView(`object`: ShowsItem) {

    }

    override fun onBindView(
        `object`: ShowsItem,
        onItemClickedListener: IBaseClickListener<ShowsItem>
    ) {

        val itemShowsBinding = viewDataBinding as RvItemShowsBinding

        Glide.with(itemView)
            .load(`object`.image.medium)
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

        itemShowsBinding.rvItemShowsName.text = `object`.name

        val avgRating = (`object`.rating.average / 10) * 5
        itemShowsBinding.rvItemShowsRating.rating = avgRating.toFloat()
        itemShowsBinding.rvItemShowsStatus.text = `object`.status
        val genres = `object`.genres.joinToString(", ")
        itemShowsBinding.rvItemShowsGenres.text = genres

        attachListenerWithCustomView(itemShowsBinding.rvItemForwardToDetails , onItemClickedListener, `object`)
        attachListenerWithCustomView(itemShowsBinding.rvItemClear , onItemClickedListener, `object`)
    }

    override fun onViewRecycled() {

    }

}
package com.imran.tvmaze.ui.shows

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.imran.tvmaze.R
import com.imran.tvmaze.databinding.FragmentInfoBinding
import com.imran.tvmaze.model.Shows
import com.imran.tvmaze.model.ShowsItem
import com.imran.tvmaze.ui.base.BaseFragment
import com.imran.tvmaze.utils.DateUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : BaseFragment<FragmentInfoBinding>() {

    private lateinit var fragmentInfoBinding: FragmentInfoBinding

    private lateinit var item: ShowsItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            item = it.getSerializable("data") as ShowsItem
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateShowsInfo()
    }

    private fun populateShowsInfo() {

        Glide.with(requireContext())
            .load(item.image.original)
            .override(
                fragmentInfoBinding.ivShowsCoverPhoto.measuredWidth,
                fragmentInfoBinding.ivShowsCoverPhoto.measuredHeight
            )
            .error(R.drawable.baseline_local_movies_green_600_24dp)
            .into(fragmentInfoBinding.ivShowsCoverPhoto)

        Glide.with(requireContext())
            .load(item.image.medium)
            .override(
                fragmentInfoBinding.ivShowsCoverPoster.measuredWidth,
                fragmentInfoBinding.ivShowsCoverPoster.measuredHeight
            )
            .error(R.drawable.baseline_local_movies_green_600_24dp)
            .into(fragmentInfoBinding.ivShowsCoverPoster)

        fragmentInfoBinding.tvShowsName.text = item.name
        fragmentInfoBinding.tvShowsRuntime.text = String.format("%s Minutes", item.runtime)

        val avgRating = ((item.rating?.average ?: 0.0) / 10) * 5
        fragmentInfoBinding.rbShowsRating.rating = avgRating.toFloat()
        val genres = item.genres.joinToString(", ")
        fragmentInfoBinding.tvShowsGenres.text = genres

        fragmentInfoBinding.tvShowsType.text = item.type
        fragmentInfoBinding.tvShowsStatus.text = item.status
        fragmentInfoBinding.tvShowsLang.text = item.language
        fragmentInfoBinding.tvShowsPremiered.text = DateUtil.getFormattedDate(item.premiered)

        val summaryText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String.format(
                "%s", Html.fromHtml(
                    item.summary,
                    Html.FROM_HTML_MODE_LEGACY
                )
            )
        } else {
            String.format("%s", Html.fromHtml(item.summary))
        }
        fragmentInfoBinding.tvShowsSummary.text = summaryText

        val days = item.schedule?.days?.joinToString(" ,")

        fragmentInfoBinding.tvShowsSchedule.text =
            String.format("%s %s", days, item.schedule?.time?.let { DateUtil.getFormattedTime(it) })

        fragmentInfoBinding.tvShowsNetwork.text =
            String.format("%s, %s", item.network?.name, item.network?.country?.code)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_info
    }

    override fun intViewBinding(viewBinding: FragmentInfoBinding) {
        this.fragmentInfoBinding = viewBinding
    }
}
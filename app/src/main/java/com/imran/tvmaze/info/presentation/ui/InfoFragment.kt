package com.imran.tvmaze.info.presentation.ui

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.imran.tvmaze.R
import com.imran.tvmaze.databinding.FragmentInfoBinding
import com.imran.tvmaze.core.base.model.Show
import com.imran.tvmaze.core.base.BaseFragment
import com.imran.tvmaze.core.db.domain.model.Genre
import com.imran.tvmaze.core.network.Result
import com.imran.tvmaze.core.utils.DateUtil
import com.imran.tvmaze.core.utils.Priority
import com.imran.tvmaze.info.presentation.viewmodel.InfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InfoFragment : BaseFragment<FragmentInfoBinding>() {

    @Inject
    lateinit var infoViewModel: InfoViewModel

    private lateinit var item: Show

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            item = it.getSerializable("data") as Show
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_info, menu)

        val menuItem = menu.findItem(R.id.action_menu_favorite)
        val checkBox = menuItem.actionView as CheckBox

        checkBox.buttonDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.bookmark_button_background)
        checkBox.isChecked = item.isFavorite

        checkBox.setOnClickListener() { _ ->
            run {
                infoViewModel.insertFavorite(item).observe(viewLifecycleOwner){
                    when (it.status){
                        Result.Status.SUCCESS -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            checkBox.isChecked = it.data!!
                        }
                        Result.Status.LOADING -> {

                        }
                        Result.Status.ERROR -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            checkBox.isChecked = item.isFavorite
                        }
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.action_menu_favorite -> {

            }
        }
        return super.onOptionsItemSelected(menuItem)
    }

    override fun onViewCreated(viewBinding: FragmentInfoBinding?, savedInstanceState: Bundle?) {
        populateShowsInfo(viewBinding!!)
    }

    private fun populateShowsInfo(fragmentInfoBinding : FragmentInfoBinding) {

        Glide.with(requireContext())
            .load(item.image?.original)
            .override(
                fragmentInfoBinding.ivShowsCoverPhoto.measuredWidth,
                fragmentInfoBinding.ivShowsCoverPhoto.measuredHeight
            )
            .error(R.drawable.ic_local_movies_green_600_24dp)
            .into(fragmentInfoBinding.ivShowsCoverPhoto)

        Glide.with(requireContext())
            .load(item.image?.medium)
            .override(
                fragmentInfoBinding.ivShowsCoverPoster.measuredWidth,
                fragmentInfoBinding.ivShowsCoverPoster.measuredHeight
            )
            .error(R.drawable.ic_local_movies_green_600_24dp)
            .into(fragmentInfoBinding.ivShowsCoverPoster)

        fragmentInfoBinding.tvShowsName.text = item.name
        fragmentInfoBinding.tvShowsRuntime.text = String.format("%s Minutes", item.runtime)

        val avgRating = (item.rating?.average?.div(10))?.times(5)
        if (avgRating != null) {
            fragmentInfoBinding.rbShowsRating.rating = avgRating.toFloat()
        }
        val genres = item.genres?.joinToString(", ")
        fragmentInfoBinding.tvShowsGenres.text = genres

        if (item.genres != null && item.genres?.size!! > 0){
            for (s in item.genres!!){
                val genre = Genre(name = s, priority = Priority.low, points = 1.toFloat() / item.genres?.size!!)
                infoViewModel.insertGenre(genre)
            }
        }

        fragmentInfoBinding.tvShowsType.text = item.type
        fragmentInfoBinding.tvShowsStatus.text = item.status
        fragmentInfoBinding.tvShowsLang.text = item.language
        fragmentInfoBinding.tvShowsPremiered.text = DateUtil.getFormattedDate(item.premiered!!)

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
}
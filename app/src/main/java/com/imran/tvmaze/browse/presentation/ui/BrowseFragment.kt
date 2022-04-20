package com.imran.tvmaze.browse.presentation.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imran.tvmaze.R
import com.imran.tvmaze.core.base.model.Show
import com.imran.tvmaze.core.adapter.BaseRecyclerAdapter
import com.imran.tvmaze.core.adapter.BaseViewHolder
import com.imran.tvmaze.core.adapter.IBaseClickListener
import com.imran.tvmaze.core.base.BaseFragment
import com.imran.tvmaze.browse.presentation.viewholder.BrowseViewHolder
import com.imran.tvmaze.browse.presentation.viewmodel.BrowseViewModel
import com.imran.tvmaze.core.network.Result
import com.imran.tvmaze.databinding.FragmentBrowseBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BrowseFragment : BaseFragment<FragmentBrowseBinding>() {

    private lateinit var fragmentBrowseBinding: FragmentBrowseBinding

    private lateinit var baseRecyclerAdapter: BaseRecyclerAdapter<Show, IBaseClickListener<Show>>

    @Inject
    lateinit var browseViewModel : BrowseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentBrowseBinding.rvShows.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            adapter = baseRecyclerAdapter
        }

        // fetch shows from TVMaze API
        fetchTVShows()
    }

    private fun fetchTVShows() {
        browseViewModel.findShows(page = "1").observe(viewLifecycleOwner){ result ->
            when (result.status){
                Result.Status.SUCCESS -> {
                    baseRecyclerAdapter.update(result.data!!)
                }
                else -> {

                }
            }
        }
    }

    private fun initAdapter() {
        baseRecyclerAdapter = object : BaseRecyclerAdapter<Show, IBaseClickListener<Show>>(
            null,
            tvItemClickListener
        ){
            override fun onCreateView(
                parent: ViewGroup?,
                viewType: Int): BaseViewHolder<Show, IBaseClickListener<Show>> {
                return BrowseViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent?.context), R.layout.rv_item_browse, parent, false
                    )
                )
            }

            override fun areSameItems(oldItem: Show?, newItem: Show?): Boolean {
                return oldItem?.id == newItem?.id
            }

            override fun areSameContents(oldItem: Show?, newItem: Show?): Boolean {
                return oldItem == newItem
            }

            override fun onCreateEmptyView(
                parent: ViewGroup?,
                viewType: Int
            ): BaseViewHolder<Show, IBaseClickListener<Show>> {
                return BrowseViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent?.context), R.layout.rv_item_empty, parent, false
                    )
                )
            }
        }
        baseRecyclerAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_browse
    }

    override fun intViewBinding(viewBinding: FragmentBrowseBinding) {
        this.fragmentBrowseBinding = viewBinding
    }

    val tvItemClickListener = object : IBaseClickListener<Show> {
        override fun onItemClicked(view: View?, item: Show, position: Int) {
            when(view!!.id){
                R.id.rv_item_clear -> {
                    openAlertDialog(item)
                    return
                }

                R.id.rv_item_forward_to_details -> {
                    // navigate to TV-shows details info page
                    val extras = Bundle()
                    extras.putSerializable("data", item)
                    extras.putString("title", item.name)
                    val navController = Navigation.findNavController(
                        activity!!,
                        R.id.navHostFragment
                    )
                    navController.navigate(R.id.action_showsFragment_to_infoFragment, extras)
                    return
                }
            }
        }
    }

    private fun openAlertDialog(item: Show) {
        val alertDialog = AlertDialog.Builder(context, R.style.MazeAlertDialogTheme)
        alertDialog.apply {
            setTitle("Delete ${item.name}")
            setMessage("Do you really want to remove the entry from the list?")
            setPositiveButton("Yes") { _, _ ->
                // temporarily clear one item from the list
                baseRecyclerAdapter.remove(item)
            }
            setNegativeButton("No", null)
            setIcon(R.drawable.baseline_warning_red_900_24dp)
            show()
        }
    }

    companion object {
        private const val TAG = "BrowseFragment"
    }
}
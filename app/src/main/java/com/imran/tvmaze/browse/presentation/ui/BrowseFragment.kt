package com.imran.tvmaze.browse.presentation.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.imran.tvmaze.MainActivity
import com.imran.tvmaze.R
import com.imran.tvmaze.browse.presentation.utils.WrapContentLinearLayoutManager
import com.imran.tvmaze.core.base.model.Show
import com.imran.tvmaze.core.adapter.BaseRecyclerAdapter
import com.imran.tvmaze.core.adapter.BaseViewHolder
import com.imran.tvmaze.core.adapter.IBaseClickListener
import com.imran.tvmaze.core.base.BaseFragment
import com.imran.tvmaze.browse.presentation.viewholder.BrowseViewHolder
import com.imran.tvmaze.browse.presentation.viewmodel.BrowseViewModel
import com.imran.tvmaze.core.adapter.RecyclerPagination
import com.imran.tvmaze.core.network.Result
import com.imran.tvmaze.databinding.FragmentBrowseBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BrowseFragment : BaseFragment<FragmentBrowseBinding>() {

    private var baseRecyclerAdapter: BaseRecyclerAdapter<Show, IBaseClickListener<Show>>? = null

    private lateinit var searchView: SearchView

    @Inject
    lateinit var browseViewModel : BrowseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_browse, menu)

        val menuItem = menu.findItem(R.id.action_menu_search)
        searchView = menuItem.actionView as SearchView
        searchView.queryHint = "Search TV"

        searchView.setOnCloseListener {
            (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
            false
        }

        searchView.setOnSearchClickListener {
            (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    browseViewModel.searchShows(query = query).observe(viewLifecycleOwner){
                        when (it.status){
                            Result.Status.SUCCESS -> {
                                baseRecyclerAdapter!!.update(it.data!!)
                            }
                            else -> {}
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                if (!searchView.isIconified){
                    searchView.isIconified = true
                    fetchTVShows(0, 0, null)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(viewBinding: FragmentBrowseBinding?, savedInstanceState: Bundle?) {
        initAdapter()
        viewBinding!!.rvShows.apply {
            layoutManager = WrapContentLinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            adapter = baseRecyclerAdapter
            setOnScrollListener(RecyclerPagination(onLoadMore = { page, scrollPosition ->
                fetchTVShows(page, scrollPosition, viewBinding)
            }))
        }

        // fetch shows from TVMaze API
        if (browseViewModel.tvShowList.value == null){
            fetchTVShows(0, 0, viewBinding)
        } else {
            baseRecyclerAdapter!!.update(browseViewModel.tvShowList.value!!.data!!)
        }

        viewBinding.pullToRefresh.setOnRefreshListener {
            if (viewBinding.pullToRefresh.isRefreshing){
                // fetch shows from TVMaze API
                fetchTVShows(0, 0, viewBinding)
                viewBinding.pullToRefresh.isRefreshing = false
            }
        }
    }

    private fun fetchTVShows(page: Int, scrollPosition: Int, viewBinding: FragmentBrowseBinding?) {
        browseViewModel.findShows(page = "$page").observe(viewLifecycleOwner){ result ->
            when (result.status){
                Result.Status.SUCCESS -> {
                    if (page > 0){
                        baseRecyclerAdapter!!.add(result.data!!, callback = { startPosition, itemCount, diffResult, adapter ->
                            viewBinding?.rvShows?.post {
                                baseRecyclerAdapter!!.notifyItemRangeInserted(startPosition, itemCount)
                                diffResult.dispatchUpdatesTo(adapter)
                            }
                        })
                        viewBinding!!.rvShows.scrollToPosition(scrollPosition)
                    } else {
                        baseRecyclerAdapter!!.update(result.data!!)
                    }
                }
                else -> {

                }
            }
        }
    }

    private fun initAdapter() {
        baseRecyclerAdapter = object : BaseRecyclerAdapter<Show, IBaseClickListener<Show>>(mutableListOf(), tvItemClickListener){
            override fun onLoadedView(parent: ViewGroup): BaseViewHolder<Show, IBaseClickListener<Show>> {
                return BrowseViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.rv_item_browse, parent, false
                    )
                )
            }

            override fun onLoadingView(parent: ViewGroup): BaseViewHolder<Show, IBaseClickListener<Show>> {
                TODO("Not yet implemented")
            }

            override fun onInfiniteLoadingView(parent: ViewGroup): BaseViewHolder<Show, IBaseClickListener<Show>> {
                TODO("Not yet implemented")
            }

            override fun onEmptyView(parent: ViewGroup): BaseViewHolder<Show, IBaseClickListener<Show>> {
                return BrowseViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.rv_item_empty, parent, false
                    )
                )
            }
        }
        baseRecyclerAdapter!!.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun getLayoutRes(): Int = R.layout.fragment_browse

    private val tvItemClickListener = object : IBaseClickListener<Show> {
        override fun onItemClicked(view: View?, item: Show, position: Int) {
            when(view!!.id){
                /*R.id.rv_item_clear -> {
                    openAlertDialog(item)
                    return
                }*/
                R.id.rv_item_forward_to_details -> {
                    // navigate to TV-shows details info page
                    val extras = Bundle()
                    extras.putSerializable("data", item)
                    extras.putString("title", item.name)
                    val navController = Navigation.findNavController(
                        requireActivity(),
                        R.id.navHostFragment
                    )
                    navController.navigate(R.id.action_showsFragment_to_infoFragment, extras)
                    return
                }
            }
        }
    }

    private fun openAlertDialog(item: Show) {
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MazeAlertDialogTheme)
        alertDialog.apply {
            setTitle("Delete ${item.name}")
            setMessage("Do you really want to remove the entry from the list?")
            setPositiveButton("Yes") { _, _ ->
                // temporarily clear one item from the list
                baseRecyclerAdapter!!.remove(item)
            }
            setNegativeButton("No", null)
            setIcon(R.drawable.baseline_warning_red_900_24dp)
            show()
        }
    }

    companion object {
        private const val TAG = "BrowseFragment"
    }

    override fun onDestroyView() {
        baseRecyclerAdapter = null
        super.onDestroyView()
    }
}
package com.imran.tvmaze.browse.presentation.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BrowseFragment : BaseFragment<FragmentBrowseBinding>() {

    private var baseRecyclerAdapter: BaseRecyclerAdapter<Show, IBaseClickListener<Show>>? = null

    private lateinit var searchView: SearchView

    @Inject
    lateinit var browseViewModel : BrowseViewModel

    private var page = 0

    private lateinit var observer : Observer<Result<List<Show>>>

    private var isMenuExpanded = false

    private var searchQuery = ""

    private var isQuerySubmitted = false

    private var isLoadMore = false

    override fun getLayoutRes(): Int = R.layout.fragment_browse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_browse, menu)

        val menuItem = menu.findItem(R.id.action_menu_search)
        searchView = menuItem.actionView as SearchView
        searchView.queryHint = "Search TV"

        if (isMenuExpanded){
            (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            searchView.isIconified = false
            searchView.setQuery(searchQuery, false)
        }

        searchView.setOnCloseListener {
            (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
            false
        }
        searchView.setOnSearchClickListener {
            (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            isMenuExpanded = true
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchQuery = query
                    browseViewModel.searchShows(query = query).observe(viewLifecycleOwner){
                        when (it.status){
                            Result.Status.SUCCESS -> {
                                baseRecyclerAdapter!!.update(it.data!!)
                                isQuerySubmitted = true
                            }
                            else -> {

                            }
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    searchQuery = newText
                }
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                if (!searchView.isIconified){
                    if (isQuerySubmitted) fetchTVShows(page)
                    // reset view to default
                    resetUi(null)
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
            setOnScrollListener(RecyclerPagination(onLoadMore = { page, _ ->
                CoroutineScope(Dispatchers.Main).launch {
                    if (!isLoadMore){
                        isLoadMore = true
                        fetchTVShows(page)
                    }
                }
            }, scrollToTop = { isTop ->
                if (isTop) {
                    viewBinding.scrollToTop.hide()
                } else {
                    viewBinding.scrollToTop.show()
                }
            }))
        }

        observer = Observer { result ->
            when (result.status){
                Result.Status.SUCCESS -> {
                    baseRecyclerAdapter!!.update(result.data!!)
                    if (page >= 1) {
                        viewBinding.rvShows.smoothScrollBy(0, 100)
                        isLoadMore = false
                    }
                    viewBinding.pullToRefresh.isRefreshing = false
                }
                Result.Status.LOADING -> {
                    viewBinding.pullToRefresh.isRefreshing = true
                }
                else -> {}
            }
        }

        // fetch shows from TVMaze API
        if (browseViewModel.tvShowList.value?.data == null){
            fetchTVShows(page)
        }
        browseViewModel.tvShowList.observe(viewLifecycleOwner, observer)

        viewBinding.pullToRefresh.setOnRefreshListener {
            if (viewBinding.pullToRefresh.isRefreshing){
                fetchTVShows(page)
                // reset view to default
                resetUi(viewBinding)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (searchView.isIconfiedByDefault){
                    if (isQuerySubmitted) fetchTVShows(page)
                    // reset view to default
                    resetUi(null)
                }
            }
        })

        viewBinding.scrollToTop.setOnClickListener {
            viewBinding.rvShows.smoothScrollToPosition(0)
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
                return BrowseViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.rv_item_loading, parent, false
                    )
                )
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

    private fun resetUi(viewBinding: FragmentBrowseBinding?) {
        searchView.isIconified = true
        isMenuExpanded = false
        isQuerySubmitted = false
        if (searchView.isIconfiedByDefault) searchView.isIconified = true
        if (viewBinding != null)
            viewBinding.pullToRefresh.isRefreshing = false
    }

    private fun fetchTVShows(page: Int) {
        this.page = page
        browseViewModel.findShows(page = page)
    }

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
            setIcon(R.drawable.ic_warning_red_900_24dp)
            show()
        }
    }

    companion object {
        private const val TAG = "BrowseFragment"
    }

    override fun onPause() {
        browseViewModel.tvShowList.removeObserver(observer)
        super.onPause()
    }

    override fun onDestroyView() {
        baseRecyclerAdapter = null
        super.onDestroyView()
    }
}
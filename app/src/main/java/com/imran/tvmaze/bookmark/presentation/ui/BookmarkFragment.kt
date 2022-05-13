package com.imran.tvmaze.bookmark.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.imran.tvmaze.R
import com.imran.tvmaze.bookmark.presentation.viewholder.BookmarkViewHolder
import com.imran.tvmaze.bookmark.presentation.viewmodel.BookmarkViewModel
import com.imran.tvmaze.browse.presentation.utils.WrapContentLinearLayoutManager
import com.imran.tvmaze.core.adapter.BaseRecyclerAdapter
import com.imran.tvmaze.core.adapter.BaseViewHolder
import com.imran.tvmaze.core.adapter.IBaseClickListener
import com.imran.tvmaze.core.base.BaseFragment
import com.imran.tvmaze.core.db.domain.model.Bookmark
import com.imran.tvmaze.core.network.Result
import com.imran.tvmaze.databinding.FragmentBookmarkBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>() {

    override fun getLayoutRes(): Int = R.layout.fragment_bookmark

    @Inject
    lateinit var bookmarkViewModel: BookmarkViewModel

    private var bookmarkAdapter: BaseRecyclerAdapter<Bookmark, IBaseClickListener<Bookmark>>? = null

    override fun onViewCreated(viewBinding: FragmentBookmarkBinding?, savedInstanceState: Bundle?) {

        initBookmarkAdapter()

        viewBinding!!.rvBookmark.apply {
            layoutManager = WrapContentLinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            adapter = bookmarkAdapter
        }

        bookmarkViewModel.bookmarks.observe(viewLifecycleOwner){ result ->
            when(result.status){
                Result.Status.SUCCESS -> {
                    bookmarkAdapter!!.update(result.data!!)
                    viewBinding.pullToRefresh.isRefreshing = false
                }
                Result.Status.LOADING -> {
                    viewBinding.pullToRefresh.isRefreshing = true
                }
                else -> {
                    viewBinding.pullToRefresh.isRefreshing = false
                    viewBinding.rvBookmark.visibility = View.GONE
                    viewBinding.tvNoContentMessage.visibility = View.VISIBLE
                    viewBinding.tvNoContentMessage.text = result.message
                    //Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        bookmarkViewModel.getBookmarks()

        viewBinding.pullToRefresh.setOnRefreshListener {
            if (viewBinding.pullToRefresh.isRefreshing){
                bookmarkViewModel.getBookmarks()
                // reset view to default
                viewBinding.pullToRefresh.isRefreshing = false
            }
        }
    }

    private fun initBookmarkAdapter() {
        bookmarkAdapter = object : BaseRecyclerAdapter<Bookmark, IBaseClickListener<Bookmark>>(mutableListOf(), itemClickListener){
            override fun onLoadedView(parent: ViewGroup): BaseViewHolder<Bookmark, IBaseClickListener<Bookmark>> {
                return BookmarkViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.rv_item_browse, parent, false
                    )
                )
            }

            override fun onLoadingView(parent: ViewGroup): BaseViewHolder<Bookmark, IBaseClickListener<Bookmark>> {
                return BookmarkViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.rv_item_loading, parent, false
                    )
                )
            }

            override fun onInfiniteLoadingView(parent: ViewGroup): BaseViewHolder<Bookmark, IBaseClickListener<Bookmark>> {
                return BookmarkViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.rv_item_loading, parent, false
                    )
                )
            }

            override fun onEmptyView(parent: ViewGroup): BaseViewHolder<Bookmark, IBaseClickListener<Bookmark>> {
                return BookmarkViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.rv_item_empty, parent, false
                    )
                )
            }
        }
        bookmarkAdapter!!.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private val itemClickListener = object : IBaseClickListener<Bookmark> {
        override fun onItemClicked(view: View?, item: Bookmark, position: Int) {
            when(view!!.id){
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
}
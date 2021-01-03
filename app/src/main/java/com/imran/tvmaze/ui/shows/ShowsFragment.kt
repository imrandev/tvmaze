package com.imran.tvmaze.ui.shows

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imran.tvmaze.R
import com.imran.tvmaze.databinding.FragmentShowsBinding
import com.imran.tvmaze.model.ShowsItem
import com.imran.tvmaze.ui.adapter.BaseRecyclerAdapter
import com.imran.tvmaze.ui.adapter.BaseViewHolder
import com.imran.tvmaze.ui.adapter.IBaseClickListener
import com.imran.tvmaze.ui.base.BaseFragment
import com.imran.tvmaze.ui.holder.ShowsRecyclerHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ShowsFragment : BaseFragment<FragmentShowsBinding>() {

    private lateinit var fragmentShowsBinding: FragmentShowsBinding

    private lateinit var baseRecyclerAdapter: BaseRecyclerAdapter<ShowsItem, IBaseClickListener<ShowsItem>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentShowsBinding.rvShows.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            adapter = baseRecyclerAdapter
        }

        // fetch shows from TVMaze API
        fetchShows()
    }

    private fun fetchShows() {
        showsViewModel.findShows().observe(viewLifecycleOwner){
            lifecycleScope.launch {
                withContext(Dispatchers.Main){
                    baseRecyclerAdapter.update(it.toMutableList())
                }
            }
        }
    }

    private fun initAdapter() {
        baseRecyclerAdapter = object : BaseRecyclerAdapter<ShowsItem, IBaseClickListener<ShowsItem>>(
            null,
            showItemClickListener
        ){
            override fun onCreateView(
                parent: ViewGroup?,
                viewType: Int): BaseViewHolder<ShowsItem, IBaseClickListener<ShowsItem>> {
                return ShowsRecyclerHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent?.context), R.layout.rv_item_shows, parent, false
                    )
                )
            }

            override fun areSameItems(oldItem: ShowsItem?, newItem: ShowsItem?): Boolean {
                return oldItem?.id == newItem?.id
            }

            override fun areSameContents(oldItem: ShowsItem?, newItem: ShowsItem?): Boolean {
                return oldItem == newItem
            }

            override fun onCreateEmptyView(
                parent: ViewGroup?,
                viewType: Int
            ): BaseViewHolder<ShowsItem, IBaseClickListener<ShowsItem>> {
                return ShowsRecyclerHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent?.context), R.layout.rv_item_empty, parent, false
                    )
                )
            }
        }
        baseRecyclerAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_shows
    }

    override fun intViewBinding(viewBinding: FragmentShowsBinding) {
        this.fragmentShowsBinding = viewBinding
    }

    val showItemClickListener = object : IBaseClickListener<ShowsItem> {
        override fun onItemClicked(view: View?, item: ShowsItem, position: Int) {

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

    private fun openAlertDialog(item: ShowsItem) {
        val alertDialog = AlertDialog.Builder(context, R.style.MazeAlertDialogTheme)
        alertDialog.apply {
            setTitle("Delete ${item.name}")
            setMessage("Do you really want to remove the entry from the list?")
            setPositiveButton("Yes") { dialog, which ->
                // temporarily clear one item from the list
                baseRecyclerAdapter.remove(item)
            }
            setNegativeButton("No", null)
            setIcon(R.drawable.baseline_warning_red_900_24dp)
            show()
        }
    }

    companion object {
        private const val TAG = "ShowsFragment"
    }
}
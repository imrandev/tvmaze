package com.imran.tvmaze.menu.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.imran.tvmaze.R
import com.imran.tvmaze.browse.presentation.utils.WrapContentLinearLayoutManager
import com.imran.tvmaze.core.adapter.BaseRecyclerAdapter
import com.imran.tvmaze.core.adapter.BaseViewHolder
import com.imran.tvmaze.core.adapter.IBaseClickListener
import com.imran.tvmaze.core.base.BaseFragment
import com.imran.tvmaze.databinding.FragmentMenuBinding
import com.imran.tvmaze.menu.domain.model.MenuItem
import com.imran.tvmaze.menu.presentation.viewholder.MenuViewHolder
import com.imran.tvmaze.menu.presentation.viewmodel.MenuViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding>() {

    @Inject
    lateinit var menuViewModel: MenuViewModel

    private var baseRecyclerAdapter: BaseRecyclerAdapter<MenuItem, IBaseClickListener<MenuItem>>? = null

    override fun getLayoutRes() = R.layout.fragment_menu

    override fun onViewCreated(viewBinding: FragmentMenuBinding?, savedInstanceState: Bundle?) {

        initAdapter()

        viewBinding!!.rvMore.apply {
            layoutManager = WrapContentLinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            adapter = baseRecyclerAdapter
        }

        menuViewModel.getMenus().observe(viewLifecycleOwner){
            baseRecyclerAdapter?.update(it)
        }
    }

    private fun initAdapter() {
        baseRecyclerAdapter = object : BaseRecyclerAdapter<MenuItem, IBaseClickListener<MenuItem>>(mutableListOf(), menuItemClickListener){
            override fun onLoadedView(parent: ViewGroup): BaseViewHolder<MenuItem, IBaseClickListener<MenuItem>> {
                return MenuViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.rv_item_menu, parent, false
                    )
                )
            }

            override fun onLoadingView(parent: ViewGroup): BaseViewHolder<MenuItem, IBaseClickListener<MenuItem>> {
                return MenuViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.rv_item_loading, parent, false
                    )
                )
            }

            override fun onInfiniteLoadingView(parent: ViewGroup): BaseViewHolder<MenuItem, IBaseClickListener<MenuItem>> {
                TODO("Not yet implemented")
            }

            override fun onEmptyView(parent: ViewGroup): BaseViewHolder<MenuItem, IBaseClickListener<MenuItem>> {
                return MenuViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.rv_item_empty, parent, false
                    )
                )
            }
        }
        baseRecyclerAdapter!!.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private val menuItemClickListener = object : IBaseClickListener<MenuItem> {
        override fun onItemClicked(view: View?, item: MenuItem, position: Int) {

        }
    }
}
package com.imran.tvmaze.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.imran.tvmaze.browse.presentation.viewmodel.BrowseViewModel

/**
 * Created by Imran Khan on 12/25/2020.
 * Email : context.imran@gmail.com
 */

abstract class BaseFragment<V : ViewDataBinding> : Fragment() {

    protected lateinit var fragmentContext : Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.fragmentContext = context
    }

    @LayoutRes
    protected abstract fun getLayoutRes() : Int
    protected abstract fun intViewBinding(viewBinding: V)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewDataBinding = DataBindingUtil.inflate<V>(inflater, getLayoutRes(), container, false)
        intViewBinding(viewDataBinding)
        return viewDataBinding.root
    }
}
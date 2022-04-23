package com.imran.tvmaze.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Created by Imran Khan on 12/25/2020.
 * Email : context.imran@gmail.com
 */

abstract class BaseFragment<V : ViewDataBinding> : Fragment() {

    @LayoutRes
    protected abstract fun getLayoutRes() : Int

    private var viewDataBinding : V? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate<V>(inflater, getLayoutRes(), container, false)
        return viewDataBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated(viewDataBinding, savedInstanceState)
    }

    abstract fun onViewCreated(viewBinding: V?, savedInstanceState: Bundle?)

    override fun onDestroyView() {
        viewDataBinding = null
        super.onDestroyView()
    }
}
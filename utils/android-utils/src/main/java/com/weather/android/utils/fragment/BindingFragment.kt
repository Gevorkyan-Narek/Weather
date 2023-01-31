package com.weather.android.utils.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import org.koin.androidx.scope.ScopeFragment

abstract class BindingFragment<B : ViewBinding> : ScopeFragment() {

    var binding: B? = null

    abstract fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = onCreateBinding(inflater, container)
        return binding?.root
    }

    open fun B.initView() {
        // empty
    }

    open fun B.observeViewModel() {
        // empty
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            initView()
            observeViewModel()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}
package com.example.android.utils.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BindingFragmentMVVM<B : ViewBinding> : Fragment() {

    var binding: B? = null

    abstract fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = onCreateBinding(inflater, container)
        binding?.initView()
        return binding?.root
    }

    open fun B.initView() {
        // empty
    }

    open fun observeViewModel() {
        // empty
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}
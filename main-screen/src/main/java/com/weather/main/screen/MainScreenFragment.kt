package com.weather.main.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import com.weather.android.utils.fragment.BindingFragmentMVVM
import com.weather.main.screen.databinding.MainScreenBinding

class MainScreenFragment : BindingFragmentMVVM<MainScreenBinding>() {

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): MainScreenBinding = MainScreenBinding.inflate(inflater, container, false)



}
package com.weather.main.screen.today

import android.view.LayoutInflater
import android.view.ViewGroup
import com.weather.android.utils.fragment.BindingFragmentMVVM
import com.weather.main.screen.databinding.TodayScreenBinding

class TodayFragment : BindingFragmentMVVM<TodayScreenBinding>() {

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): TodayScreenBinding = TodayScreenBinding.inflate(inflater, container, false)

}
package com.weather.main.screen.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.weather.android.utils.fragment.BindingFragmentMVVM
import com.weather.android.utils.setupWithViewPager2
import com.weather.main.screen.databinding.MainScreenBinding

class MainScreenFragment : BindingFragmentMVVM<MainScreenBinding>() {

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): MainScreenBinding = MainScreenBinding.inflate(inflater, container, false)

    override fun MainScreenBinding.initView() {
        viewPager.adapter = MainScreenFragmentPagerAdapter(childFragmentManager, lifecycle)
        tabLayout.setupWithViewPager2(
            viewPager,
            WeatherFragmentsEnum.values().map { title -> getString(title.titleId) }
        )

    }

}
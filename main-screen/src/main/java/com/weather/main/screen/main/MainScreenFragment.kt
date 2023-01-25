package com.weather.main.screen.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.weather.android.utils.fragment.BindingFragmentMVVM
import com.weather.android.utils.observe
import com.weather.android.utils.setupWithViewPager2
import com.weather.main.screen.databinding.MainScreenBinding
import com.weather.main.screen.dialog.CityDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenFragment : BindingFragmentMVVM<MainScreenBinding>() {

    private val viewModel: MainScreenViewModel by viewModel()

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
        location.setOnClickListener {
            viewModel.locationClicked()
        }
    }

    override fun MainScreenBinding.observeViewModel() {
        viewModel.run {
            observe(locationMenuLiveData) {
                CityDialog().show(parentFragmentManager, "CityDialog")
            }
            observe(cityTitle) { cityName ->
                title.text = cityName
            }
        }
    }

}
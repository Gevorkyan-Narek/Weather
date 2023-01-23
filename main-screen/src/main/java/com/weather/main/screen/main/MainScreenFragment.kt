package com.weather.main.screen.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.weather.android.utils.fragment.BindingFragmentMVVM
import com.weather.android.utils.observe
import com.weather.android.utils.setupWithViewPager2
import com.weather.main.screen.databinding.MainScreenBinding
import com.weather.main.screen.main.adapter.CitiesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenFragment : BindingFragmentMVVM<MainScreenBinding>() {

    private val viewModel: MainScreenViewModel by viewModel()

    private val citiesAdapter: CitiesAdapter by lazy {
        CitiesAdapter(viewModel::citySelected)
    }

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
        menuButton.setOnClickListener {
            viewModel.menuClicked()
        }
        sideMenu.cityRecycler.adapter = citiesAdapter
    }

    override fun MainScreenBinding.observeViewModel() {
        viewModel.run {
            observe(cityTitle) { cityName ->
                title.text = cityName
            }
            observe(menuVisibilityLiveData) {
                sideMenu.root.run {
                    visibility = if (isVisible) View.INVISIBLE else View.VISIBLE
                }
            }
            observe(cities) { cities ->
                citiesAdapter.submitList(cities)
            }
        }
    }

}
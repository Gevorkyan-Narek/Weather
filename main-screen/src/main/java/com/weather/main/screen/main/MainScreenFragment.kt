package com.weather.main.screen.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.weather.android.utils.fragment.BindingFragment
import com.weather.android.utils.observe
import com.weather.android.utils.setupWithViewPager2
import com.weather.main.screen.databinding.FMainScreenBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenFragment : BindingFragment<FMainScreenBinding>() {

    private val viewModel: MainScreenViewModel by viewModel()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FMainScreenBinding = FMainScreenBinding.inflate(inflater, container, false)

    override fun FMainScreenBinding.initView() {
        viewPager.adapter = MainScreenFragmentPagerAdapter(childFragmentManager, lifecycle)
        viewPager.isUserInputEnabled = false
        tabLayout.setupWithViewPager2(
            viewPager,
            WeatherFragmentsEnum.values().map { title -> getString(title.titleId) }
        )
        location.setOnClickListener {
            viewModel.locationClicked()
        }
    }

    override fun FMainScreenBinding.observeViewModel() {
        viewModel.run {
            observe(openBottomSheetFragment) { navigationTo ->
                findNavController().navigate(navigationTo.actionId)
            }
            observe(cityTitle) { cityName ->
                title.text = cityName
            }
            observe(isDownloadLoadingWarning) { isDownloading ->
                downloadWarning.isVisible = isDownloading
            }
        }
    }

}
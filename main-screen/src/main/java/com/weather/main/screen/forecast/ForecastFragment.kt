package com.weather.main.screen.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import com.weather.android.utils.fragment.BindingFragment
import com.weather.android.utils.observe
import com.weather.main.screen.databinding.FForecastScreenBinding
import com.weather.main.screen.forecast.adapter.ForecastAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForecastFragment : BindingFragment<FForecastScreenBinding>() {

    private val viewModel: ForecastViewModel by viewModel()

    private val adapter = ForecastAdapter()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FForecastScreenBinding = FForecastScreenBinding.inflate(inflater, container, false)

    override fun FForecastScreenBinding.initView() {
        forecastRecycler.adapter = adapter
    }

    override fun FForecastScreenBinding.observeViewModel() {
        viewModel.run {
            observe(forecastLiveData) { list ->
                adapter.submitList(list)
            }
        }
    }

}
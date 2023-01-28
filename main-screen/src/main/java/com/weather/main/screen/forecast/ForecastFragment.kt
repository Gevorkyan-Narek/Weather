package com.weather.main.screen.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import com.weather.android.utils.fragment.BindingFragment
import com.weather.android.utils.observe
import com.weather.main.screen.databinding.ForecastScreenBinding
import com.weather.main.screen.forecast.adapter.ForecastAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForecastFragment : BindingFragment<ForecastScreenBinding>() {

    private val viewModel: ForecastViewModel by viewModel()

    private val adapter = ForecastAdapter()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): ForecastScreenBinding = ForecastScreenBinding.inflate(inflater, container, false)

    override fun ForecastScreenBinding.initView() {
        forecastRecycler.adapter = adapter
    }

    override fun ForecastScreenBinding.observeViewModel() {
        viewModel.run {
            observe(forecastLiveData) { list ->
                adapter.submitList(list)
            }
        }
    }

}
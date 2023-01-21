package com.weather.main.screen.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import com.weather.android.utils.fragment.BindingFragmentMVVM
import com.weather.main.screen.databinding.ForecastScreenBinding

class ForecastFragment : BindingFragmentMVVM<ForecastScreenBinding>() {

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): ForecastScreenBinding = ForecastScreenBinding.inflate(inflater, container, false)

}
package com.weather.main.screen.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.weather.main.screen.forecast.ForecastFragment
import com.weather.main.screen.today.TodayFragment

class MainScreenFragmentPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val todayFragment by lazy { TodayFragment() }
    private val forecastFragment by lazy { ForecastFragment() }

    override fun getItemCount(): Int = WeatherFragmentsEnum.values().size

    override fun createFragment(position: Int): Fragment = when (position) {
        WeatherFragmentsEnum.TODAY.ordinal -> todayFragment
        WeatherFragmentsEnum.FORECAST.ordinal -> forecastFragment
        else -> throw NoSuchElementException()
    }
}
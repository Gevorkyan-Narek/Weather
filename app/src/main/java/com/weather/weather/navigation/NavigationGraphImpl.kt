package com.weather.weather.navigation

import com.weather.navigation.NavigationGraph
import com.weather.weather.R

class NavigationGraphImpl : NavigationGraph {

    override val startScreen: Int
        get() = R.id.startScreenFragment

    override val mainScreen: Int
        get() = R.id.mainScreenFragment

    override val cityBottomSheetFragment: Int
        get() = R.id.cityBottomSheetFragment

}
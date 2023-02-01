package com.weather.navigation

import androidx.annotation.IdRes

interface NavigationGraph {

    val startScreen: Int
        @IdRes get

    val mainScreen: Int
        @IdRes get

    val cityBottomSheetFragment: Int
        @IdRes get

}
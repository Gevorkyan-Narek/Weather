package com.weather.navigation

import androidx.annotation.IdRes

interface IssueGraphNavigation {

    val startScreen: Int
        @IdRes get

    val mainScreen: Int
        @IdRes get

}
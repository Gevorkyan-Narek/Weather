package com.weather.weather.navigation

import com.weather.navigation.IssueGraphNavigation
import com.weather.weather.R

class IssueGraphNavigationImpl : IssueGraphNavigation {

    override val startScreen: Int
        get() = R.id.startScreenFragment

    override val mainScreen: Int
        get() = R.id.mainScreenFragment

}
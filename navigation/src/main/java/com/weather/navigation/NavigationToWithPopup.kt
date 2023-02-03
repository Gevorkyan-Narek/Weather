package com.weather.navigation

import androidx.annotation.IdRes

sealed class NavigationInfo {

    class NavigationTo(@IdRes val actionId: Int) : NavigationInfo()

    class NavigationToWithPopup(
        @IdRes val actionId: Int,
        @IdRes val popupTo: Int,
        val inclusive: Boolean,
    ) : NavigationInfo()

}
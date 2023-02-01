package com.weather.navigation

import androidx.annotation.IdRes

class NavigationToWithPopup(
    @IdRes val actionId: Int,
    @IdRes val popupTo: Int,
    val inclusive: Boolean,
)
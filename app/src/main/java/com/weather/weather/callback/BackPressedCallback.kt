package com.weather.weather.callback

import androidx.activity.OnBackPressedCallback

class BackPressedCallback(
    private val action: () -> Unit,
) : OnBackPressedCallback(true) {

    override fun handleOnBackPressed() {
        action()
    }

}
package com.weather.android.utils

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

fun View.toBottomSheetBehaviour() = BottomSheetBehavior.from(this)
package com.weather.android.utils

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

fun TabLayout.setupWithViewPager2(viewPager2: ViewPager2, titles: List<String>) {
    TabLayoutMediator(this, viewPager2) { tab, position ->
        tab.text = titles[position]
    }.attach()
}
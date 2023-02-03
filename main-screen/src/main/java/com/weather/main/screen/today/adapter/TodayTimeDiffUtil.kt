package com.weather.main.screen.today.adapter

import androidx.recyclerview.widget.DiffUtil
import com.weather.main.screen.model.WeatherPres

class TodayTimeDiffUtil : DiffUtil.ItemCallback<WeatherPres>() {

    override fun areItemsTheSame(oldItem: WeatherPres, newItem: WeatherPres): Boolean {
        return oldItem.dateTime == newItem.dateTime
    }

    override fun areContentsTheSame(oldItem: WeatherPres, newItem: WeatherPres): Boolean {
        return oldItem == newItem
    }
}
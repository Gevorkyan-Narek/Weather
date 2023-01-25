package com.weather.main.screen.forecast.adapter

import androidx.recyclerview.widget.DiffUtil

class ForecastDiffUtil : DiffUtil.ItemCallback<ForecastItemPres>() {
    override fun areItemsTheSame(oldItem: ForecastItemPres, newItem: ForecastItemPres): Boolean {
        return oldItem.dateTime == newItem.dateTime
    }

    override fun areContentsTheSame(oldItem: ForecastItemPres, newItem: ForecastItemPres): Boolean {
        return oldItem == newItem
    }

}
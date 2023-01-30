package com.weather.main.screen.forecast.adapter

import androidx.recyclerview.widget.RecyclerView
import com.weather.android.utils.getString
import com.weather.android.utils.setWeatherIcon
import com.weather.base.utils.DateFormatter
import com.weather.main.screen.R
import com.weather.main.screen.databinding.ForecastItemBinding

class ForecastViewHolder(private val binding: ForecastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ForecastItemPres) {
        binding.run {
            dayOfWeek.text = DateFormatter.getDayOfWeek(item.dateTime)
            date.text = DateFormatter.getDayMonth(item.dateTime)
            setWeatherIcon(item.icon, icon)
            tempMinMax.text = getString(R.string.tempMaxMin, item.tempMax, item.tempMin)
            humidity.text = root.context.getString(R.string.withPercent, item.humidity)
            description.text = item.description.replaceFirstChar(Char::titlecase)
        }
    }

}
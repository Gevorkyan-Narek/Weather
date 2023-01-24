package com.weather.main.screen.forecast.adapter

import androidx.recyclerview.widget.RecyclerView
import com.weather.android.utils.setWeatherIcon
import com.weather.main.screen.R
import com.weather.main.screen.databinding.ForecastItemBinding
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.TextStyle
import java.util.*

class ForecastViewHolder(private val binding: ForecastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ForecastItemPres) {
        binding.run {
            dayOfWeek.text = item.dateTime.dayOfWeek.getDisplayName(TextStyle.SHORT_STANDALONE,
                Locale.forLanguageTag("ru"))

            date.text = item.dateTime.format(DateTimeFormatter.ofPattern("MMMM d"))
            setWeatherIcon(item.icon, icon)
            tempMinMax.text = root.context.getString(R.string.tempMaxMin, item.tempMax, item.tempMin)
            humidity.text = root.context.getString(R.string.withPercent, item.humidity)
            description.text = item.description.replaceFirstChar(Char::titlecase)
        }
    }

}
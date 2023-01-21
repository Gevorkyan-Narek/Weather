package com.weather.main.screen.today.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.weather.android.utils.setWeatherIcon
import com.weather.android.utils.themeColor
import com.weather.main.screen.R
import com.weather.main.screen.databinding.WeatherItemBinding
import com.weather.main.screen.model.WeatherPres
import org.threeten.bp.format.DateTimeFormatter

class TodayTimeViewHolder(
    private val binding: WeatherItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(pres: WeatherPres, isSelected: Boolean, action: (WeatherPres) -> Unit) {
        binding.run {
            time.text = pres.dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
            temp.text = root.context.getString(R.string.tempWithoutCelsius, pres.metrics.temp)
            setWeatherIcon(pres.shortInfo.first().icon, icon)
            root.run {
                strokeColor = context.getStrokeColor(isSelected)
                setCardBackgroundColor(context.getBackgroundColor(isSelected))
                setOnClickListener {
                    action(pres)
                }
            }
        }
    }

    private fun Context.getStrokeColor(isSelected: Boolean): Int = themeColor(
        if (isSelected) R.attr.colorOnSecondary
        else R.attr.colorSecondaryVariant
    )

    private fun Context.getBackgroundColor(isSelected: Boolean) = themeColor(
        if (isSelected) R.attr.colorSecondary
        else R.attr.colorSecondaryVariant
    )

}
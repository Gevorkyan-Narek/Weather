package com.weather.main.screen.today.adapter

import androidx.recyclerview.widget.RecyclerView
import com.weather.android.utils.getColorIf
import com.weather.android.utils.getString
import com.weather.base.utils.DateFormatter
import com.weather.main.screen.R
import com.weather.main.screen.databinding.WeatherItemBinding
import com.weather.main.screen.model.WeatherPres

class TodayTimeViewHolder(
    private val binding: WeatherItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(pres: WeatherPres, isSelected: Boolean, action: (WeatherPres) -> Unit) {
        binding.run {
            time.text = DateFormatter.getTime(pres.dateTime)
            temp.text = getString(R.string.tempWithoutCelsius, pres.metrics.temp)
            pres.shortInfo?.icon?.run {
                icon.setImageResource(this)
            }
            root.run {
                strokeColor =
                    getColorIf(isSelected, R.attr.colorOnSecondary, R.attr.colorSecondaryVariant)

                setCardBackgroundColor(
                    getColorIf(
                        isSelected,
                        R.attr.colorSecondary,
                        R.attr.colorSecondaryVariant)
                )

                setOnClickListener {
                    action(pres)
                }
            }
        }
    }

}
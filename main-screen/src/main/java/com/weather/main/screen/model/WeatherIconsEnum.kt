package com.weather.main.screen.model

import androidx.annotation.DrawableRes
import com.weather.main.screen.R

enum class WeatherIconsEnum(
    @DrawableRes val dayRes: Int,
    @DrawableRes val nightRes: Int,
    vararg val id: String,
) {

    CLEAR_SKY(R.drawable.ic_day, R.drawable.ic_night, "01"),
    FEW_CLOUDS(R.drawable.ic_cloudy_day, R.drawable.ic_cloudy_night, "02"),
    CLOUDY(R.drawable.ic_cloudy, R.drawable.ic_cloudy, "03", "04"),
    RAIN(R.drawable.ic_rain, R.drawable.ic_rain, "09"),
    SHOWER_RAIN(R.drawable.ic_rain_day, R.drawable.ic_rain, "10"),
    THUNDER(R.drawable.ic_thunder, R.drawable.ic_thunder, "11"),
    SNOW(R.drawable.ic_snow, R.drawable.ic_snow, "13"),
    MIST(R.drawable.ic_mist, R.drawable.ic_mist, "50");

    companion object {

        fun getDrawable(id: String?): Int? {
            return id?.let {
                val enum = values().find { enum -> enum.id.contains(id.dropLast(1)) }
                if (isDay(id.last())) enum?.dayRes
                else enum?.nightRes
            }
        }

        private fun isDay(value: Char): Boolean {
            return value == 'd'
        }

    }

}
package com.weather.core.datasource.db.forecast.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.weather.base.utils.fromJson
import com.weather.core.datasource.db.forecast.model.WeatherShortInfoEntity

class WeatherShortInfoConverter {

    private val gson = Gson()

    @TypeConverter
    fun toJson(shortInfo: List<WeatherShortInfoEntity>): String {
        return gson.toJson(shortInfo)
    }

    @TypeConverter
    fun fromJson(shortInfo: String): List<WeatherShortInfoEntity> {
        return gson.fromJson(shortInfo)
    }

}
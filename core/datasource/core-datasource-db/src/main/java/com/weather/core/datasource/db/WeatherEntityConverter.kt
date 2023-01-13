package com.weather.core.datasource.db

import androidx.room.TypeConverter

class WeatherEntityConverter {

    @TypeConverter
    fun toJson(description: List<String>): String {
        return description.joinToString(";")
    }

    @TypeConverter
    fun fromJson(description: String): List<String> {
        return description.split(";")
    }
}
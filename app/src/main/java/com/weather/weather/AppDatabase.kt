package com.weather.weather

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.weather.core.datasource.db.forecast.ForecastDao
import com.weather.core.datasource.db.forecast.WeatherEntity
import com.weather.core.datasource.db.forecast.WeatherEntityConverter
import com.weather.core.datasource.db.geo.CityDao
import com.weather.core.datasource.db.geo.CityEntity

@Database(
    entities = [
        WeatherEntity::class,
        CityEntity::class
    ],
    version = 6,
    exportSchema = false
)
@TypeConverters(
    WeatherEntityConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "WeatherDB"

        fun createDatabase(context: Context): AppDatabase {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

    }

    abstract fun forecastDao(): ForecastDao
    abstract fun cityDao(): CityDao
}
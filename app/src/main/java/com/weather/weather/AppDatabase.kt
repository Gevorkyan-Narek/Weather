package com.weather.weather

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.weather.core.datasource.db.forecast.ForecastDao
import com.weather.core.datasource.db.forecast.model.WeatherEntity
import com.weather.core.datasource.db.geo.CityDao
import com.weather.core.datasource.db.geo.CityEntity

@Database(
    entities = [
        WeatherEntity::class,
        CityEntity::class
    ],
    version = 12,
    exportSchema = false
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
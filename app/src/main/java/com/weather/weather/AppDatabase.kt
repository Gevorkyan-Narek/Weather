package com.weather.weather

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.weather.core.datasource.db.ForecastDao
import com.weather.core.datasource.db.WeatherEntity
import com.weather.core.datasource.db.WeatherEntityConverter

@Database(
    entities = [
        WeatherEntity::class
    ],
    version = 4,
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
}
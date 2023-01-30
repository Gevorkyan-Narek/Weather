package com.weather.core.datasource.db.forecast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weather.core.datasource.db.forecast.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entity: List<WeatherEntity>)

    @Query("DELETE FROM WeatherEntity")
    suspend fun clearAll()

    @Query("SELECT * FROM WeatherEntity WHERE date > :date")
    fun getNearestForecast(date: Long): Flow<List<WeatherEntity>>

    @Query("SELECT * FROM WeatherEntity")
    fun getForecast(): Flow<List<WeatherEntity>>

}
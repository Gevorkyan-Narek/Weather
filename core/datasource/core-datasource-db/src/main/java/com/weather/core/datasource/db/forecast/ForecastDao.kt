package com.weather.core.datasource.db.forecast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entity: List<WeatherEntity>)

    @Query("DELETE FROM WeatherEntity")
    suspend fun clearAll()

    @Query("SELECT * FROM WeatherEntity WHERE date == :day")
    fun getForecastByDay(day: String): Flow<List<WeatherEntity>>
}
package com.weather.core.datasource.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entity: List<WeatherEntity>)

    @Query("DELETE FROM WeatherEntity")
    suspend fun clearAll()
}
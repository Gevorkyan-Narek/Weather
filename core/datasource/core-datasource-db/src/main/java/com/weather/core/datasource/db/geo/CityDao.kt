package com.weather.core.datasource.db.geo

import androidx.room.Dao
import androidx.room.Query
import com.weather.core.datasource.db.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao : BaseDao<CityEntity> {

    @Query("SELECT * FROM CityEntity")
    fun getCities(): Flow<List<CityEntity>>

}
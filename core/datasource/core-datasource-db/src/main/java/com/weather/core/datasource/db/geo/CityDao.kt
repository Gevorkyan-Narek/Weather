package com.weather.core.datasource.db.geo

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.weather.core.datasource.db.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao : BaseDao<CityEntity> {

    @Query("SELECT * FROM CityEntity WHERE isSelected = 1 LIMIT 1")
    fun selectedCities(): Flow<CityEntity?>

    @Query("SELECT * FROM CityEntity")
    fun getCities(): Flow<List<CityEntity>>

    @Query("SELECT * FROM CityEntity WHERE name = :cityName")
    suspend fun getCity(cityName: String): CityEntity?

    @Query("UPDATE CityEntity SET isSelected = 0")
    suspend fun disableSelectedCities()

    @Transaction
    suspend fun updateSelectedCity(city: CityEntity) {
        disableSelectedCities()
        update(city.copy(isSelected = true))
    }


}
package com.weather.core.datasource.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<T> {

    @Insert
    suspend fun insert(entity: T)

    @Insert
    suspend fun insertAll(entity: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReplace(entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReplace(entity: List<T>)

}
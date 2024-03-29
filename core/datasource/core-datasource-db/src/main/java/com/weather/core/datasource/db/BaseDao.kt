package com.weather.core.datasource.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert
    suspend fun insert(entity: T)

    @Insert
    suspend fun insert(entity: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReplace(entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReplace(entity: List<T>)

    @Update
    suspend fun update(entity: T)

    @Delete
    suspend fun remove(entity: T)

    @Delete
    suspend fun remove(entity: List<T>)

}
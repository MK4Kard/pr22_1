package com.bignerdranch.android.pr22_2_kma

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RateDao {
    @Query("SELECT * FROM 'rates'")
    fun getAll(): List<RateEntity>

    @Insert
    fun insert(vararg rates: RateEntity)

    @Delete
    fun deleteRate(entity: RateEntity)

    @Query("SELECT * FROM rates WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): RateEntity?

    @Update
    suspend fun update(rate: RateEntity)
}
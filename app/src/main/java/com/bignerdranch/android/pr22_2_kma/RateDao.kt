package com.bignerdranch.android.pr22_2_kma

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RateDao {
    @Query("SELECT * FROM rates")
    fun getRates(): LiveData<List<Rate>>

    @Insert
    fun addRate(rate: Rate)

    @Query("DELETE FROM rates WHERE rateId = :id")
    fun deleteRate(id:Int)
}
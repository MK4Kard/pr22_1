package com.bignerdranch.android.pr22_2_kma

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [RateEntity::class], version = 1)

abstract class RateDb : RoomDatabase() {
    abstract fun rateDao(): RateDao

    companion object {
        fun getDb(context: Context) : RateDb {
            return Room.databaseBuilder(context, RateDb::class.java, "rate_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
    }
}
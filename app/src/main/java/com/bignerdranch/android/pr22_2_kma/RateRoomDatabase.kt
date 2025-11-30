package com.bignerdranch.android.pr22_2_kma

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Rate::class)], version = 1)
abstract class RateRoomDatabase : RoomDatabase() {
    abstract fun rateDao(): RateDao

    companion object {
        private var INSTANCE: RateRoomDatabase? = null
        fun getInstance(context: Context): RateRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RateRoomDatabase::class.java,
                        "ratesdb"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
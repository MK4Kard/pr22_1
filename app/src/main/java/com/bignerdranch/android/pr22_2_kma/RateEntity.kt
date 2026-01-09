package com.bignerdranch.android.pr22_2_kma

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rates")
data class RateEntity (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name_start: String = "",
    var data_start: Double = 0.0,
    var name_one: String = "",
    var data_one: Double = 0.0,
    var name_two: String = "",
    var data_two: Double = 0.0
)
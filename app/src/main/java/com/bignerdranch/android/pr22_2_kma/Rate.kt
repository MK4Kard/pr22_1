package com.bignerdranch.android.pr22_2_kma

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rates")
class Rate {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "rateId")
    var id: Int = 0
    @ColumnInfo(name = "rateAll")
    var name_start: String = ""
    var data_start: Double = 0.0
    var name: String = ""
    var data: Double = 0.0

    constructor() {}

    constructor(name_s: String, data_s: Double, name: String, data: Double) {
        this.name_start = name_s
        this.data_start = data_s
        this.name = name
        this.data = data
    }
}
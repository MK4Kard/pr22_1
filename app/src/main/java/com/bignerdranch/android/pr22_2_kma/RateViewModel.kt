package com.bignerdranch.android.pr22_2_kma

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class RateViewModel(application: Application) : ViewModel() {

    val rateList: LiveData<List<Rate>>
    private val repository: RateRepository
    var rateNameStart by mutableStateOf("")
    var rateDataStart by mutableStateOf(0.0)
    var rateName by mutableStateOf("")
    var rateData by mutableStateOf(0.0)

    init {
        val rateDb = RateRoomDatabase.getInstance(application)
        val rateDao = rateDb.rateDao()
        repository = RateRepository(rateDao)
        rateList = repository.rateList
    }
    fun changeNameStart(value: String) {
        rateNameStart = value
    }
    fun changeDataStart(value: String) {
        rateDataStart = value.toDoubleOrNull() ?: rateData
    }
    fun changeName(value: String) {
        rateName = value
    }
    fun changeData(value: String) {
        rateData = value.toDoubleOrNull() ?: rateData
    }
    fun addRate() {
        repository.addRate(Rate(rateNameStart, rateDataStart, rateName, rateData))
    }
    fun deleteRate(id: Int) {
        repository.deleteRate(id)
    }
}
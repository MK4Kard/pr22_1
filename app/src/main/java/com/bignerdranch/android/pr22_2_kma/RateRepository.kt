package com.bignerdranch.android.pr22_2_kma

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RateRepository(private val rateDao: RateDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val rateList: LiveData<List<Rate>> = rateDao.getRates()

    fun addRate(Rate: Rate) {
        coroutineScope.launch(Dispatchers.IO) {
            rateDao.addRate(Rate)
        }
    }

    fun deleteRate(id:Int) {
        coroutineScope.launch(Dispatchers.IO) {
            rateDao.deleteRate(id)
        }
    }
}
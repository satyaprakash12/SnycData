// TimeViewModel.kt
package com.example.snycdata.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class TimeViewModel(application: Application) : AndroidViewModel(application) {

    private val _timeList = MutableLiveData<MutableList<String>>()
    val timeList: LiveData<MutableList<String>> get() = _timeList

    init {
        _timeList.value = mutableListOf()
    }

    fun addTime(time: String) {
        _timeList.value?.let {
            it.add(time)
            _timeList.value = it
        }
    }
}

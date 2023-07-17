package com.harbourspace.unsplash.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EasterEggViewModel : ViewModel() {

    private var _number = 0
    val number = MutableLiveData<Int>()

    fun increment() {
        _number++
        number.postValue(_number)
    }
}
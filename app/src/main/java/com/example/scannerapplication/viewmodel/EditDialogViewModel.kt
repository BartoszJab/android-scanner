package com.example.scannerapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditDialogViewModel() : ViewModel() {
    val productCount = MutableLiveData(0)

    fun setCount(count: Int) {
        productCount.value = count
    }

    fun increaseCount() {
        productCount.value = productCount.value?.plus(1)
    }

    fun decreaseCount() {
        productCount.value = productCount.value?.minus(1)
    }
}
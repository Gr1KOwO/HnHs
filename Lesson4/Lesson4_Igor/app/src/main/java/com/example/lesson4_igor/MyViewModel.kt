package com.example.lesson4_igor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MyViewModel : ViewModel() {
    private val _textLiveData = MutableLiveData<String>()

    val textLiveData: LiveData<String> = _textLiveData

    fun setText(text: String) {
        _textLiveData.value = text
    }
}
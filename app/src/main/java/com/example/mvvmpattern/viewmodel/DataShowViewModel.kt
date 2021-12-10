package com.example.mvvmpattern.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmpattern.repository.DatabaseRepository

class DataShowViewModel(
    private val databaseRepository: DatabaseRepository
) : BaseViewModel() {

    private val _result = MutableLiveData<Int>()


    val result: LiveData<Int> get() = _result

    init {
        _result.postValue(0);
    }


    fun onClickDecrease() {
        _result.postValue(result.value?.minus(1))
    }

    fun onClickIncrease() {
        _result.postValue(result.value?.plus(1))
    }
}
package com.example.mvvmpattern.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmpattern.common.Event
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class BaseViewModel : ViewModel() {
    private val _viewEvent = MutableLiveData<Event<Any>>()
    val viewEvent: LiveData<Event<Any>>
        get() = _viewEvent

    companion object {
        const val SHOW_MEMO_LIST = "SHOW_MEMO_LIST"
        const val MAKE_NEW_MEMO = "MAKE_NEW_MEMO"
        const val MAKE_EDIT_MEMO = "MAKE_EDIT_MEMO"
    }

    fun viewEvent(content: Any) {
        _viewEvent.value = Event(content)
    }

    fun getCurrentTime(): String =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
}
package com.sandy.memo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sandy.memo.common.Event
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class BaseViewModel : ViewModel() {
    private val _viewEvent = MutableLiveData<Event<Any>>()
    val viewEvent: LiveData<Event<Any>> get() = _viewEvent

    companion object {
        const val SHOW_MEMO_LIST = "SHOW_MEMO_LIST"
        const val MAKE_NEW_MEMO = "MAKE_NEW_MEMO"
        const val MAKE_EDIT_MEMO = "MAKE_EDIT_MEMO"
        const val SHOW_PROGRESS_BAR = "SHOW_PROGRESS_BAR"
        const val HIDE_PROGRESS_BAR = "HIDE_PROGRESS_BAR"
        const val SHOW_DIALOG = "SHOW_DIALOG"
        const val SHOW_TOAST = "SHOW_TOAST"
        const val HIDE_KEYBOARD = "HIDE_KEYBOARD"
        const val SET_PASSWORD = "SET_PASSWORD"
    }

    fun viewEvent(content: Any) {
        _viewEvent.value = Event(content)
    }

    fun getCurrentTime(): String =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

    fun showProgressBar() = viewEvent(SHOW_PROGRESS_BAR)

    fun hideProgressBar() = viewEvent(HIDE_PROGRESS_BAR)

}
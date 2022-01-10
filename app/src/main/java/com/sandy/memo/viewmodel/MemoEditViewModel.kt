package com.sandy.memo.viewmodel

import android.appwidget.AppWidgetManager
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import com.sandy.memo.Constants
import com.sandy.memo.MyApplication.Companion.prefs
import com.sandy.memo.common.mediatorLiveData
import com.sandy.memo.common.mutableLiveData
import com.sandy.memo.data.WidgetItem
import com.sandy.memo.entity.Memo
import com.sandy.memo.repository.MemoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoEditViewModel(
    private val memoRepository: MemoRepository
) : BaseViewModel() {
    val title = mutableLiveData("")
    val content = mutableLiveData("")
    var mode = mutableLiveData(false)
    private var memo: Memo = Memo("", "", getCurrentTime(), pin = false, isPassword = false)
    var isSaveButtonEnabled = mediatorLiveData(title, content) {
        !((memo.title == title.value.toString()) && (memo.content == content.value.toString()))
    }
    var editEnabled = mediatorLiveData(mode) { !mode.value!! }
    val pinStatus = mutableLiveData(false)
    val isPassword = mutableLiveData(false)

    fun onClickSaveBtn() {
        memo.title = title.value.toString()
        memo.content = content.value.toString()
        memo.date = getCurrentTime()
        memo.pin = pinStatus.value == true
        memo.isPassword = isPassword.value == true

        CoroutineScope(Dispatchers.IO).launch {
            memo.id = memoRepository.insert(memo).toInt()
        }
        isSaveButtonEnabled.postValue(false)
        viewEvent(MEMO_SAVE_EVENT)
    }

    fun getItem(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            memo = memoRepository.getItem(id)
            content.postValue(memo.content)
            title.postValue(memo.title)
            pinStatus.postValue(memo.pin)
            isPassword.postValue(memo.isPassword)
        }
        editEnabled.postValue(false)
        changeMode()
    }

    fun onClickBackBtn() {
        if (isSaveButtonEnabled.value == true)
            viewEvent(SHOW_DIALOG)
        else
            viewEvent(SHOW_MEMO_LIST)
    }

    fun changeMode() = mode.postValue(!mode.value!!)

    fun onClickPin() {
        pinStatus.value?.let {
            val updatePin = !pinStatus.value!!
            pinStatus.postValue(updatePin)
            CoroutineScope(Dispatchers.IO).launch {
                memoRepository.updatePin(memo.id, updatePin)
            }
        }
    }

    fun onClickPassword() {
        if (prefs.getString(Constants.PASSWORD, "no-password") == "no-password") {
            viewEvent(SET_PASSWORD)
        } else {
            setPassword()
        }
    }

    fun createPassword(password: String) {
        setPassword()
        prefs.setString(Constants.PASSWORD, password)
    }

    private fun setPassword() {
        isPassword.value = isPassword.value?.let { !isPassword.value!! }
        CoroutineScope(Dispatchers.IO).launch {
            isPassword.value?.let { memoRepository.updatePassword(memo.id, it) }
        }
    }
}
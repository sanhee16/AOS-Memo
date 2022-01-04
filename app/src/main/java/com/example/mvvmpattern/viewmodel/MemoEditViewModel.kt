package com.example.mvvmpattern.viewmodel

import com.example.mvvmpattern.common.mediatorLiveData
import com.example.mvvmpattern.common.mutableLiveData
import com.example.mvvmpattern.entity.Memo
import com.example.mvvmpattern.repository.MemoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoEditViewModel(
    private val memoRepository: MemoRepository
) : BaseViewModel() {
    val title = mutableLiveData("")
    val content = mutableLiveData("")
    var mode = mutableLiveData(false)
    private var memo: Memo = Memo("", "", getCurrentTime())
    val isSaveButtonEnabled = mediatorLiveData(title) { !title.value.isNullOrEmpty() }
    var editEnabled = mediatorLiveData(mode) { !mode.value!! }

    fun onClickSaveBtn() {
        memo.title = title.value.toString()
        memo.content = content.value.toString()
        memo.date = getCurrentTime()

        CoroutineScope(Dispatchers.IO).launch {
            memoRepository.insert(memo)
        }
        viewEvent(SHOW_TOAST)
        viewEvent(HIDE_KEYBOARD)
    }

    fun getItem(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            memo = memoRepository.getItem(id)
            content.postValue(memo.content)
            title.postValue(memo.title)
        }
        editEnabled.postValue(false)
        changeMode()
    }

    fun onClickBackBtn() {
        if (checkChange())
            viewEvent(SHOW_DIALOG)
        else
            viewEvent(SHOW_MEMO_LIST)
    }

    fun changeMode() = mode.postValue(!mode.value!!)
    private fun checkChange() =
        !((memo.title == title.value.toString()) && (memo.content == content.value.toString()))
}
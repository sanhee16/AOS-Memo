package com.sandy.memo.viewmodel

import com.sandy.memo.common.mediatorLiveData
import com.sandy.memo.common.mutableLiveData
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
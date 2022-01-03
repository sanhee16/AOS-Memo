package com.example.mvvmpattern.viewmodel

import android.media.JetPlayer
import android.util.Log
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
    private var isChange = false
    var mode = mutableLiveData(true)
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
        viewEvent(SHOW_MEMO_LIST)
    }

    fun getItem(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            memo = memoRepository.getItem(id)
            content.postValue(memo.content)
            title.postValue(memo.title)
        }
        editEnabled.postValue(false)
    }

    fun onClickBackBtn() {
        if (isChange)
            viewEvent(SHOW_DIALOG)
        else
            viewEvent(SHOW_MEMO_LIST)
    }

    fun changeMode() = mode.postValue(!mode.value!!)
}
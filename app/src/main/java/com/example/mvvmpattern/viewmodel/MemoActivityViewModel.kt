package com.example.mvvmpattern.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmpattern.entity.Memo
import com.example.mvvmpattern.repository.MemoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

class MemoActivityViewModel(
    private val memoRepository: MemoRepository
) : BaseViewModel() {
    // MutableLiveData : get()/set() 가능
    private val _item = MutableLiveData<Int>()

    // liveData : get() 만 가능
    val memoList = memoRepository.getAll()
    val item: LiveData<Int> get() = _item

    companion object {
        const val SHOW_CHECK_BOX = "SHOW_CHECK_BOX"
    }
    init {
        viewEvent(SHOW_MEMO_LIST)
    }

    // MemoEditPage fragment 띄우기
    fun onClickAddBtn() = viewEvent(MAKE_NEW_MEMO)

    fun onClickDeleteBtn() {
        // 체크박스 띄우도록 해야지
        viewEvent(SHOW_CHECK_BOX)
    }

    fun onClickItem(id: Int) {
        _item.postValue(id)
        viewEvent(MAKE_EDIT_MEMO)
    }


}
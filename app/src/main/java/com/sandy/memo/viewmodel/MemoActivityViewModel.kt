package com.sandy.memo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sandy.memo.repository.MemoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoActivityViewModel(
    private val memoRepository: MemoRepository
) : BaseViewModel() {
    // MutableLiveData : get()/set() 가능
    private val _item = MutableLiveData<Int>()
    private val _isEditMode = MutableLiveData<Boolean>()

    // liveData : get() 만 가능
    var memoList = memoRepository.getAll()
    val item: LiveData<Int> get() = _item
    val isEditMode: LiveData<Boolean> get() = _isEditMode
    var notificationList = memoRepository.getPinList()

    private var checkList: MutableList<Int> = mutableListOf()

    init {
    }

    // MemoEditPage fragment 띄우기
    fun onClickAddBtn() = viewEvent(MAKE_NEW_MEMO)
    fun onClickDeleteBtn() {
        viewEvent(SHOW_DIALOG)
    }

    fun deleteMemo() {
        showProgressBar()
        CoroutineScope(Dispatchers.IO).launch {
            for (i in checkList) {
                memoRepository.deleteFromId(i)
            }
            memoList = memoRepository.getAll()
            notificationList = memoRepository.getPinList()
        }
        dismissEditMode()
        hideProgressBar()
    }

    fun onClickBackBtn() {
        checkList.clear()
        dismissEditMode()
    }

    fun onClickItem(id: Int) {
        _item.postValue(id)
        viewEvent(MAKE_EDIT_MEMO)
    }

    fun setEditMode() = _isEditMode.postValue(true)
    private fun dismissEditMode() = _isEditMode.postValue(false)

    fun getCheckList(list: MutableList<Int>) {
        checkList = list
    }

    // set notifications
    fun setNotification() {
        CoroutineScope(Dispatchers.IO).launch {
            notificationList = memoRepository.getPinList()
        }
    }

}

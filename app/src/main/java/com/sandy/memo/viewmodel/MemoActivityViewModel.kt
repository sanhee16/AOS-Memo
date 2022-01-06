package com.sandy.memo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sandy.memo.Constants
import com.sandy.memo.MyApplication
import com.sandy.memo.MyApplication.Companion.prefs
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

    var id = -1
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
        // 비밀번호 확인
        this.id = id
        CoroutineScope(Dispatchers.IO).launch {
            val isPassword = memoRepository.checkIsPassword(id)
            if (isPassword) {
                viewEvent(CHECK_PASSWORD)
            } else {
                _item.postValue(id)
            }
        }
    }

    fun checkPassword(enterPassword: String) {
        val password = prefs.getString(Constants.PASSWORD, "no-password")
        if (password == enterPassword) {
            viewEvent(RIGHT_PASSWORD)
            _item.postValue(this.id)
        } else {
            viewEvent(ALERT_WRONG_PASSWORD)
        }
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

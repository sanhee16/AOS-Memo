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
    private val _isEditMode = MutableLiveData<Boolean>()

    // liveData : get() 만 가능
    var memoList = memoRepository.getAll()
    val item: LiveData<Int> get() = _item
    val isEditMode: LiveData<Boolean> get() = _isEditMode

    private var checkList: MutableList<Int> = mutableListOf()

    init {
    }

    // MemoEditPage fragment 띄우기
//    fun onClickAddBtn() = viewEvent(MAKE_NEW_MEMO)
    fun onClickAddBtn() = viewEvent(SHOW_DIALOG)
    fun onClickDeleteBtn() {
        // check 된거 delete
        // adapter 에 checkList 에 id목록 있음 : 어떻게 가져올 건지?
        showProgressBar()
        CoroutineScope(Dispatchers.IO).launch {
            for (i in checkList) {
                memoRepository.deleteFromId(i)
            }
            memoList = memoRepository.getAll()
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

}

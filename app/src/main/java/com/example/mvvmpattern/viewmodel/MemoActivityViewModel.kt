package com.example.mvvmpattern.viewmodel

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmpattern.common.SingleLiveEvent
import com.example.mvvmpattern.model.MemoListModel
import com.example.mvvmpattern.repository.DatabaseRepository
import java.time.LocalTime

class MemoActivityViewModel(
    private val databaseRepository: DatabaseRepository
) : BaseViewModel() {
    // MutableLiveData : get()/set() 가능
    private val _memoList = MutableLiveData<MutableList<MemoListModel>>()

    // liveData : get() 만 가능
    val memoList: LiveData<MutableList<MemoListModel>> get() = _memoList
    companion object {
        const val MAKE_NEW_MEMO  = 1
    }
    init {
        // 원래는 여기서 data를 room을 통해 database에서 가져와야 한다.
        updateMemoList(makeSampleData())
    }

    // MemoEditPage fragment 띄우기
    fun addMemoList() = viewEvent(MAKE_NEW_MEMO)

    fun deleteMemoList(item: MemoListModel) {

    }

    fun updateMemoList(list : MutableList<MemoListModel>) {
        _memoList.postValue(list)
    }

    private fun makeSampleData(): MutableList<MemoListModel> {
        val memoList: MutableList<MemoListModel> = mutableListOf()
        val currentTime: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalTime.now().toString()
        } else {
            TODO("VERSION.SDK_INT < O")
            "Sample-Time"
        }
        memoList.add(MemoListModel(1, "title1", currentTime))
        memoList.add(MemoListModel(2, "title2", currentTime))
        memoList.add(MemoListModel(3, "title3", currentTime))
        memoList.add(MemoListModel(4, "title4", currentTime))
        memoList.add(MemoListModel(5, "title5", currentTime))
        memoList.add(MemoListModel(6, "title6", currentTime))
        memoList.add(MemoListModel(7, "title7", currentTime))
        memoList.add(MemoListModel(8, "title8", currentTime))
        return memoList
    }
}
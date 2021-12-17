package com.example.mvvmpattern.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmpattern.dao.MemoDao
import com.example.mvvmpattern.database.AppDatabase
import com.example.mvvmpattern.entity.Memo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 *
 */
class MemoRepository(application: Application) {
    private val memoDao: MemoDao
    private val memoList: LiveData<List<Memo>>

    init {
        val db = AppDatabase.getInstance(application)
        memoDao = db!!.memoDao()
        memoList = db.memoDao().getAll()
    }

    fun insert(memo: Memo) {
        memoDao.insert(memo)
    }

    fun delete(memo: Memo) {
        memoDao.delete(memo)
    }

    fun getAll(): LiveData<List<Memo>> {
        return memoDao.getAll()
    }

    fun getItem(id: Int) : Memo {
        return memoDao.getItem(id)
    }
}
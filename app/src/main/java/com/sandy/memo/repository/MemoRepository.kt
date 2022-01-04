package com.sandy.memo.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.sandy.memo.dao.MemoDao
import com.sandy.memo.database.AppDatabase
import com.sandy.memo.entity.Memo

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

    fun deleteFromId(id: Int){
        return memoDao.deleteFromId(id)
    }
}
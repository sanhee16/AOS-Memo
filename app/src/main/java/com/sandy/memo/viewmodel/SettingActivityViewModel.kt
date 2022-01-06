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

class SettingActivityViewModel(
    private val memoRepository: MemoRepository
) : BaseViewModel() {

    fun onClickSetPassword() {

    }
}

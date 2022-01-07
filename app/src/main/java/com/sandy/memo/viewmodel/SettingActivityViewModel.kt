package com.sandy.memo.viewmodel

import com.sandy.memo.Constants
import com.sandy.memo.MyApplication.Companion.prefs
import com.sandy.memo.repository.MemoRepository

class SettingActivityViewModel(
    private val memoRepository: MemoRepository
) : BaseViewModel() {

    fun onClickSetPassword() {
        viewEvent(SET_PASSWORD)
    }

    fun createPassword(password: String) {
        prefs.setString(Constants.PASSWORD, password)
    }
}

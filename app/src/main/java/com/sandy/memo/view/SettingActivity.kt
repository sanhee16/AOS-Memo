package com.sandy.memo.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sandy.memo.BaseActivity
import com.sandy.memo.Constants
import com.sandy.memo.R
import com.sandy.memo.adapter.MemoListAdapter
import com.sandy.memo.databinding.ActivityMemoBinding
import com.sandy.memo.databinding.ActivitySettingBinding
import com.sandy.memo.entity.Memo
import com.sandy.memo.util.DialogType1
import com.sandy.memo.util.EnterPassword
import com.sandy.memo.viewmodel.BaseViewModel.Companion.ALERT_WRONG_PASSWORD
import com.sandy.memo.viewmodel.BaseViewModel.Companion.CHECK_PASSWORD
import com.sandy.memo.viewmodel.BaseViewModel.Companion.HIDE_PROGRESS_BAR
import com.sandy.memo.viewmodel.BaseViewModel.Companion.MAKE_NEW_MEMO
import com.sandy.memo.viewmodel.BaseViewModel.Companion.RIGHT_PASSWORD
import com.sandy.memo.viewmodel.BaseViewModel.Companion.SHOW_DIALOG
import com.sandy.memo.viewmodel.BaseViewModel.Companion.SHOW_PROGRESS_BAR
import com.sandy.memo.viewmodel.MemoActivityViewModel
import com.sandy.memo.viewmodel.SettingActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingActivity : BaseActivity() {
    private lateinit var b: ActivitySettingBinding
    private val vm: SettingActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        observeLiveData()
    }

    private fun initDataBinding() {
        b = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        b.lifecycleOwner = this
        b.vm = this.vm

    }

    private fun observeLiveData() {
    }
}
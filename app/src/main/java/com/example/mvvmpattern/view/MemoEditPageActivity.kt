package com.example.mvvmpattern.view

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmpattern.BaseActivity
import com.example.mvvmpattern.Constants
import com.example.mvvmpattern.R
import com.example.mvvmpattern.databinding.ActivityMemoEditPageBinding
import com.example.mvvmpattern.entity.Memo
import com.example.mvvmpattern.viewmodel.BaseViewModel.Companion.SHOW_MEMO_LIST
import com.example.mvvmpattern.viewmodel.MemoEditViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemoEditPageActivity : BaseActivity() {
    private lateinit var b: ActivityMemoEditPageBinding
    private val vm: MemoEditViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.extras?.let { it ->
            it.getBundle(Constants.BUNDLE)?.getInt(Constants.SEND_ID)?.let { vm.getItem(it) }
        }
        initDataBinding()
        observeLiveData()
    }


    private fun initDataBinding() {
        b = DataBindingUtil.setContentView(this, R.layout.activity_memo_edit_page)
        b.lifecycleOwner = this
        b.vm = this.vm
    }

    private fun observeLiveData() {
        // todo 저장이 끝나면 나가도록 수정하기
        vm.viewEvent.observe(this, {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    SHOW_MEMO_LIST -> {
                        startActivityWithFinish<MemoActivity>()
                    }
                }
            }
        })
    }
}
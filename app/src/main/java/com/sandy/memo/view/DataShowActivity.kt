package com.sandy.memo.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sandy.memo.BaseActivity
import com.sandy.memo.R
import com.sandy.memo.databinding.ActivityDataShowBinding
import com.sandy.memo.viewmodel.DataShowViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataShowActivity : BaseActivity() {
    private lateinit var b: ActivityDataShowBinding
    private val vm: DataShowViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        observeLiveData()
    }

    private fun initDataBinding() {
        b = DataBindingUtil.setContentView(this, R.layout.activity_data_show)
        b.lifecycleOwner = this
        b.vm = this.vm
    }

    private fun observeLiveData() {
        vm.result.observe(this, { b.textResult.text = it.toString() })
    }
}
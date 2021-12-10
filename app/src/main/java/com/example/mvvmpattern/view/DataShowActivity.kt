package com.example.mvvmpattern.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mvvmpattern.BaseActivity
import com.example.mvvmpattern.R
import com.example.mvvmpattern.databinding.ActivityDataShowBinding
import com.example.mvvmpattern.viewmodel.DataShowViewModel
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
package com.sandy.memo.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sandy.memo.BaseActivity
import com.sandy.memo.R
import com.sandy.memo.databinding.ActivitySettingBinding
import com.sandy.memo.util.EnterPassword
import com.sandy.memo.viewmodel.BaseViewModel.Companion.SET_PASSWORD
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
        vm.viewEvent.observe(this, {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    SET_PASSWORD -> {
                        val enterPassword = EnterPassword(this)
                        enterPassword.start(
                            this.resources.getString(R.string.check_password),
                            this.resources.getString(R.string.wrong_password)
                        )
                        enterPassword.setClickListener(object : EnterPassword.OnClickListener {
                            override fun onConfirmClick(password: String) {
                                enterPassword.dismiss()
                                vm.createPassword(password)
                            }

                            override fun onClickCancel() {
                                enterPassword.dismiss()
                            }
                        })
                    }
                }
            }
        })
    }
}
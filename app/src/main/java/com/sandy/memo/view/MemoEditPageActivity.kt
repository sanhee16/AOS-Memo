package com.sandy.memo.view

import android.appwidget.AppWidgetManager
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.sandy.memo.BaseActivity
import com.sandy.memo.Constants
import com.sandy.memo.R
import com.sandy.memo.databinding.ActivityMemoEditPageBinding
import com.sandy.memo.util.DialogType1
import com.sandy.memo.util.EnterPassword
import com.sandy.memo.viewmodel.BaseViewModel.Companion.HIDE_KEYBOARD
import com.sandy.memo.viewmodel.BaseViewModel.Companion.SET_PASSWORD
import com.sandy.memo.viewmodel.BaseViewModel.Companion.SHOW_DIALOG
import com.sandy.memo.viewmodel.BaseViewModel.Companion.SHOW_MEMO_LIST
import com.sandy.memo.viewmodel.BaseViewModel.Companion.SHOW_TOAST
import com.sandy.memo.viewmodel.BaseViewModel.Companion.WIDGET_UPDATE
import com.sandy.memo.viewmodel.MemoEditViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.content.ComponentName
import android.util.Log
import com.sandy.memo.widget.WidgetProvider


class MemoEditPageActivity : BaseActivity() {
    private lateinit var b: ActivityMemoEditPageBinding
    private val vm: MemoEditViewModel by viewModel()
    private lateinit var imm: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.extras?.let { it ->
            it.getBundle(Constants.BUNDLE)?.getInt(Constants.SEND_ID)?.let { vm.getItem(it) }
        }
        initDataBinding()
        observeLiveData()

        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    }


    private fun initDataBinding() {
        b = DataBindingUtil.setContentView(this, R.layout.activity_memo_edit_page)
        b.lifecycleOwner = this
        b.vm = this.vm
    }

    override fun onBackPressed() {
        super.onBackPressed()
        vm.onClickBackBtn()
    }

    private fun observeLiveData() {
        vm.viewEvent.observe(this, {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    SHOW_MEMO_LIST -> {
                        startActivityWithFinish<MemoActivity>()
                    }
                    SHOW_DIALOG -> {
                        val dlg = DialogType1(this)
                        dlg.start(this.resources.getString(R.string.exit_dialog))
                        dlg.setClickListener(object : DialogType1.OnClickListener {
                            override fun onConfirmClick() {
                                dlg.dismiss()
                                startActivityWithFinish<MemoActivity>()
                            }

                            override fun onClickCancel() {
                                dlg.dismiss()
                            }
                        })
                    }
                    SHOW_TOAST -> {
                        showToast(this.resources.getString(R.string.save_memo))
                    }
                    HIDE_KEYBOARD -> {
                        imm.hideSoftInputFromWindow(b.textContext.windowToken, 0)
                    }
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
                    WIDGET_UPDATE -> {
                        Log.v("sandy","WIDGET_UPDATE")
                        val appWidgetManager = AppWidgetManager.getInstance(this)
                        val appWidgetIds = appWidgetManager.getAppWidgetIds(
                            ComponentName(this, WidgetProvider::class.java)
                        )
                        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_listview)
                    }
                }
            }
        })
    }
}
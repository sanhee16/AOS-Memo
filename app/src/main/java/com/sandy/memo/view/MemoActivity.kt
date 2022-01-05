package com.sandy.memo.view

import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sandy.memo.BaseActivity
import com.sandy.memo.Constants
import com.sandy.memo.R
import com.sandy.memo.adapter.MemoListAdapter
import com.sandy.memo.databinding.ActivityMemoBinding
import com.sandy.memo.entity.Memo
import com.sandy.memo.util.CreatePassword
import com.sandy.memo.util.DialogType1
import com.sandy.memo.util.EnterPassword
import com.sandy.memo.viewmodel.BaseViewModel.Companion.CHECK_PASSWORD
import com.sandy.memo.viewmodel.BaseViewModel.Companion.HIDE_PROGRESS_BAR
import com.sandy.memo.viewmodel.BaseViewModel.Companion.MAKE_NEW_MEMO
import com.sandy.memo.viewmodel.BaseViewModel.Companion.SHOW_DIALOG
import com.sandy.memo.viewmodel.BaseViewModel.Companion.SHOW_PROGRESS_BAR
import com.sandy.memo.viewmodel.MemoActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemoActivity : BaseActivity() {
    private lateinit var b: ActivityMemoBinding
    private val vm: MemoActivityViewModel by viewModel()
    private lateinit var adapter: MemoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MemoListAdapter(listOf<Memo>(), vm)
        initDataBinding()
        observeLiveData()
    }

    private fun initDataBinding() {
        b = DataBindingUtil.setContentView(this, R.layout.activity_memo)
        b.lifecycleOwner = this
        b.vm = this.vm

        b.memoListRecycler.layoutManager = LinearLayoutManager(this)
        b.memoListRecycler.adapter = adapter
    }

    private fun observeLiveData() {
        vm.memoList.observe(this, {
            adapter.setIsEditMode(false)
            adapter.setData(it)
        })
        vm.notificationList.observe(this, { list ->
            cancelNotificationAll()
            for (item in list) {
                makeNotification(item.id, item.title, item.content)
            }
        })
        vm.isEditMode.observe(this, {
            b.isEditMode = it
            adapter.setIsEditMode(it)
        })
        vm.item.observe(this, {
            val bundle = Bundle()
            bundle.putInt(Constants.SEND_ID, it)
            startActivityWithFinish<MemoEditPageActivity>(bundle)
        })
        vm.viewEvent.observe(this, {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    MAKE_NEW_MEMO -> {
                        startActivityWithFinish<MemoEditPageActivity>()
                    }
                    SHOW_PROGRESS_BAR -> {
                        b.progressBar.showProgress()
                    }
                    HIDE_PROGRESS_BAR -> {
                        b.progressBar.hideProgress()
                    }
                    SHOW_DIALOG -> {
                        val dlg = DialogType1(this)
                        dlg.start(this.resources.getString(R.string.delete_memo))
                        dlg.setClickListener(object : DialogType1.OnClickListener {
                            override fun onConfirmClick() {
                                dlg.dismiss()
                                vm.deleteMemo()
                            }

                            override fun onClickCancel() {
                                dlg.dismiss()
                            }
                        })
                    }
                    CHECK_PASSWORD -> {
                        val dlg = EnterPassword(this)
                        dlg.start()
                        dlg.setClickListener(object : EnterPassword.OnClickListener {
                            override fun onConfirmClick(password: String) {
                                dlg.dismiss()
                                vm.checkPassword(password)
                            }
                            override fun onClickCancel() {
                                dlg.dismiss()
                            }
                        })
                    }
                }
            }
        })
    }
}
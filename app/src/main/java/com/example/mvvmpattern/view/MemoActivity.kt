package com.example.mvvmpattern.view

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmpattern.BaseActivity
import com.example.mvvmpattern.Constants
import com.example.mvvmpattern.R
import com.example.mvvmpattern.adapter.MemoListAdapter
import com.example.mvvmpattern.databinding.ActivityMemoBinding
import com.example.mvvmpattern.entity.Memo
import com.example.mvvmpattern.viewmodel.BaseViewModel.Companion.MAKE_NEW_MEMO
import com.example.mvvmpattern.viewmodel.MemoActivityViewModel
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
        vm.memoList.observe(this, { adapter.setData(it) })
        vm.item.observe(this, {
            val bundle = Bundle()
            bundle.putInt(Constants.SEND_ID, it)
            Log.v("sandy","get it $bundle")
            startActivityWithFinish<MemoEditPageActivity>(bundle)
        })
        vm.viewEvent.observe(this, {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    MAKE_NEW_MEMO -> {
                        startActivityWithFinish<MemoEditPageActivity>()
                    }
                    MemoActivityViewModel.SHOW_CHECK_BOX -> {
                        adapter.showCheckbox()
                    }
                }
            }
        })
    }
}
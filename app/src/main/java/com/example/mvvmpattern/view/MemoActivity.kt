package com.example.mvvmpattern.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmpattern.BaseActivity
import com.example.mvvmpattern.model.MemoListModel
import com.example.mvvmpattern.R
import com.example.mvvmpattern.adapter.MemoListAdapter
import com.example.mvvmpattern.databinding.ActivityMemoBinding
import com.example.mvvmpattern.viewmodel.MemoActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalTime

class MemoActivity : BaseActivity() {
    private lateinit var b: ActivityMemoBinding
    private val vm: MemoActivityViewModel by viewModel()
    private lateinit var adapter: MemoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MemoListAdapter(mutableListOf<MemoListModel>())
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
        vm.memoList.observe(this, Observer { adapter.setData(it) })
        vm.viewEvent.observe(this, {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    MemoActivityViewModel.MAKE_NEW_MEMO -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_view, MemoEditPage())
                            .commit()
                    }
                }
            }
        })
    }
}
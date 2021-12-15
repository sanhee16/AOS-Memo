package com.example.mvvmpattern.di

import com.example.mvvmpattern.repository.DatabaseRepository
import com.example.mvvmpattern.viewmodel.DataShowViewModel
import com.example.mvvmpattern.viewmodel.MemoActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module // dsl : domain specific language

// DI(Dependency injection) : "의존성 주입"
/*
의존성 주입 = 특정 객체의 인스턴스가 필요한 경우 이를 외부에서 생성하여 전달하는 기법

 */
val viewModelModules = module {
    viewModel { DataShowViewModel(get()) }
    viewModel { MemoActivityViewModel(get()) }
}

val repository = module {
    factory { DatabaseRepository(androidContext()) }
}

val util = module {
}
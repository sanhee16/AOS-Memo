package com.example.mvvmpattern

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import com.example.mvvmpattern.di.repository
import com.example.mvvmpattern.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

// application class
/*
application component 사이에서 공동으로 멤버들을 사용할 수 있게 해주는 공유 클래스 제공
Application 객체의 멤버는 프로세스 어디에서나 참조 가능

공통으로 전역 변수를 사용하고 싶을 때 Application 클래스를 상속받아 사용
*/
class MainApp : Application(), LifecycleObserver {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(viewModelModules, repository)
        }
    }
}
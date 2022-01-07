package com.sandy.memo.widget

import android.content.Intent
import android.util.Log
import android.widget.RemoteViewsService

class WidgetRemoteViewsService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        Log.v("sandy","service onGetViewFactory")
        return WidgetRemoteViewsFactory(this.applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        Log.v("sandy","service onCreate")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.v("sandy","service onDestroy")
    }
}
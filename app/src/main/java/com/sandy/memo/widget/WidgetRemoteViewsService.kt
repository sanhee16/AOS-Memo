package com.sandy.memo.widget

import android.content.Intent
import android.util.Log
import android.widget.RemoteViewsService

class WidgetRemoteViewsService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return WidgetRemoteViewsFactory(this.applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
    }
    override fun onDestroy() {
        super.onDestroy()
    }
}
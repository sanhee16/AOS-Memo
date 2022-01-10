package com.sandy.memo.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import com.sandy.memo.R

class WidgetProvider : AppWidgetProvider() {
//https://jinsangjin.tistory.com/8
    //https://thinking-face.tistory.com/entry/Widget%EC%97%90%EC%84%9C-Room-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4%EC%97%90-%EC%A0%91%EA%B7%BC%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { appWidgetId ->
            val views = RemoteViews(context?.packageName, R.layout.widget)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
        // IntentService : Intent 사용에 의해 실행 / 오래걸리지만 메인스레드와 관련이 없는 작업
        val serviceIntent = Intent(context, WidgetRemoteViewsService::class.java)
        val widget = RemoteViews(context?.packageName, R.layout.widget)
        widget.setRemoteAdapter(R.id.widget_listview, serviceIntent)

        appWidgetManager.updateAppWidget(appWidgetIds, widget)
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }
}
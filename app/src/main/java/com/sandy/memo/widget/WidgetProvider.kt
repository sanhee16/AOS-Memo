package com.sandy.memo.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.sandy.memo.R
import com.sandy.memo.view.MemoActivity

class WidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val serviceIntent = Intent(context, WidgetRemoteViewsService::class.java)
        val widget = RemoteViews(context?.packageName, R.layout.widget)
        widget.setRemoteAdapter(R.id.widget_listview, serviceIntent)
        val intent = Intent(context, MemoActivity::class.java)
        val clickPendingIntent = PendingIntent.getActivity(
            context, 0,
            intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        widget.setPendingIntentTemplate(R.id.widget_listview, clickPendingIntent)
        appWidgetManager.updateAppWidget(appWidgetIds, widget)
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }
}
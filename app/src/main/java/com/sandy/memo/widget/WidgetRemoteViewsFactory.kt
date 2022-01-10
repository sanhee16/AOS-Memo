package com.sandy.memo.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.sandy.memo.R
import com.sandy.memo.dao.MemoDao
import com.sandy.memo.data.WidgetItem
import com.sandy.memo.database.AppDatabase

class WidgetRemoteViewsFactory(val context: Context) :
    RemoteViewsService.RemoteViewsFactory {
    var arrayList: ArrayList<WidgetItem> = ArrayList()

    private lateinit var memoDao: MemoDao

    init {
        val memoDatabase = AppDatabase.getInstance(context)
        if (memoDatabase != null) {
            memoDao = memoDatabase.memoDao()
        }
    }

    override fun onCreate() {
    }

    override fun onDataSetChanged() {
        val getWidgetList = memoDao.getWidgetInfo()
        val widgetList = ArrayList<WidgetItem> ()
        getWidgetList.forEach { item ->
            widgetList.add(WidgetItem(item.id, item.title))
        }
        arrayList = widgetList
    }

    override fun onDestroy() {
    }

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val widget = RemoteViews(context.packageName, R.layout.widget_item)
        val dataIntent = Intent()
        widget.setTextViewText(R.id.text_title, arrayList[position].title)

        dataIntent.putExtra("id", arrayList[position].id)
        dataIntent.putExtra("title", arrayList[position].title)
        widget.setOnClickFillInIntent(R.id.text_title, dataIntent)
        return widget
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }
}
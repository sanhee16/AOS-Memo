package com.sandy.memo.widget

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.sandy.memo.R
import com.sandy.memo.dao.MemoDao
import com.sandy.memo.data.WidgetItem
import org.koin.core.component.getScopeName
import android.appwidget.AppWidgetManager
import com.sandy.memo.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


class WidgetRemoteViewsFactory(val context: Context) : RemoteViewsService.RemoteViewsFactory {
    var arrayList: ArrayList<WidgetItem> = ArrayList()


    fun setData(arrayList: ArrayList<WidgetItem>) {
        this.arrayList = arrayList
    }


    fun setData() {

    }

    override fun onCreate() {
        Log.v("sandy","onCreate")
        setData()
    }

    /**
     * data변경이 일어났을 때 호출됨
     */
    override fun onDataSetChanged() {
        Log.v("sandy","onDataSetChanged")
        setData()
    }

    override fun onDestroy() {
    }

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val widget = RemoteViews(context.packageName, R.layout.widget_item)
        val dataIntent = Intent()
        dataIntent.putExtra("id", arrayList[position].id)
        dataIntent.putExtra("title", arrayList[position].title)
        widget.setOnClickFillInIntent(R.id.text1, dataIntent);
        return widget
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
        return false
    }
}
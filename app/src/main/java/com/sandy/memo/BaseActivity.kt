package com.sandy.memo

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.sandy.memo.widget.WidgetProvider



open class BaseActivity : AppCompatActivity() {
    lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
    }

    inline fun <reified T : Activity> Context.startActivity() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }

    inline fun <reified T : Activity> Context.startActivityWithFinish() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
        finish()
    }

    inline fun <reified T : Activity> Context.startActivityWithFinish(bundle: Bundle) {
        val intent = Intent(this, T::class.java)
        intent.putExtra(Constants.BUNDLE, bundle)
        startActivity(intent)
        finish()
    }

    fun View.showProgress() {
        this.visibility = View.VISIBLE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun View.hideProgress() {
        this.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }


    private fun createNotificationChannel() {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                Constants.PRIMARY_CHANNEL_ID,
                "TEST NOTIFICATION",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.enableLights(false)
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun makeNotification(notification_id: Int, title: String, content: String) {
        notificationManager.notify(
            notification_id,
            NotificationCompat.Builder(this, Constants.PRIMARY_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setOngoing(true)
                .setSmallIcon(R.drawable.read_mode).build()
        )
    }

    fun cancelNotification(cancelId: Int) {
        // https://mine-it-record.tistory.com/242
        notificationManager.cancel(cancelId)
    }

    fun cancelNotificationAll() {
        notificationManager.cancelAll()
    }

    fun Context.updateWidget() {
        val appWidgetManager = AppWidgetManager.getInstance(applicationContext)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
            ComponentName(applicationContext, WidgetProvider::class.java)
        )
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_listview)
    }
}
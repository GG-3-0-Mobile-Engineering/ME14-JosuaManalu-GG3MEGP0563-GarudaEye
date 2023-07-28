package com.yosha10.final_project.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.yosha10.final_project.R

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "notify-task"
        const val NOTIFICATION_ID = 10
    }

    override fun doWork(): Result {
        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = applicationContext.getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = "Status Darurat"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext,
        NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Warning")
            .setSmallIcon(R.drawable.icon_notifications)
            .build()
        notificationManager.notify(NOTIFICATION_ID, notification)

        return Result.success()
    }
}
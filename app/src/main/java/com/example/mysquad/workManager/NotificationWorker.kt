package com.example.mysquad.workManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.mysquad.R
import com.example.mysquad.data.localRoom.database.AppDatabase
import com.example.mysquad.data.localRoom.entity.EventEntity
import java.util.Calendar
import java.util.concurrent.TimeUnit

class NotificationWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val db = AppDatabase.getInstance(applicationContext)
        val todayEvents: List<EventEntity> = db.eventDao().getTodayEventsBlocking()

        if (todayEvents.isNotEmpty()) {
            showNotification("PlaySquad Reminder", "You have ${todayEvents.size} event(s) today!")
        }
        return Result.success()
    }

    private fun showNotification(title: String, message: String) {
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "mysquad_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "MySquad", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        manager.notify(1, notification)
    }
}

fun sendNotification(message: String, context: Context) {
    val data = workDataOf("message" to message)

    val request = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInputData(data)
        .build()

    WorkManager.getInstance(context).enqueue(request)
}

fun dailyReminder(context: Context) {
    val request = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.DAYS)
        .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "daily_event_reminder",
        ExistingPeriodicWorkPolicy.REPLACE,
        request
    )
}

fun calculateInitialDelay(): Long {
    val now = Calendar.getInstance()
    val target = now.clone() as Calendar
    target.set(Calendar.HOUR_OF_DAY, 7)
    target.set(Calendar.MINUTE, 0)
    target.set(Calendar.SECOND, 0)

    if (target.before(now)) target.add(Calendar.DAY_OF_YEAR, 1)

    return target.timeInMillis - now.timeInMillis
}
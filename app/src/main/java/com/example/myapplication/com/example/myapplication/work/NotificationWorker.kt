package com.example.myapplication.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.R

class NotificationWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {

        val notification = NotificationCompat.Builder(applicationContext, "grad_channel")
            .setContentTitle("Daily Motivation")
            .setContentText("Keep coding, you're doing great!")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        NotificationManagerCompat.from(applicationContext)
            .notify(1, notification)

        return Result.success()
    }
}
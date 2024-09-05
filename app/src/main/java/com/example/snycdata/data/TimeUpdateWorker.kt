// TimeUpdateWorker.kt
package com.example.snycdata.data

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TimeUpdateWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        private val objects = listOf("Object 1", "Object 2", "Object 3", "Object 4")
        private var currentIndex = 0
    }

    override fun doWork(): Result {
        if (currentIndex < objects.size) {
            val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            val timeWithObject = "$currentTime"

            // Post event to EventBus
            EventBus.getDefault().post(TimeModel(timeWithObject))

            currentIndex++
            if (currentIndex < objects.size) {
                scheduleNextUpdate()
            }
        }

        return Result.success()
    }

    private fun scheduleNextUpdate() {
        val workRequest = OneTimeWorkRequestBuilder<TimeUpdateWorker>()
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }
}

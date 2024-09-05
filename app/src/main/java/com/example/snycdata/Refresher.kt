package com.example.snycdata

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class Refresher(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        // Reschedule the worker to run again after 10 seconds
        scheduleNextUpdate()

        return Result.success()
    }

    private fun scheduleNextUpdate() {
        val workRequest = OneTimeWorkRequestBuilder<Refresher>()
            .setInitialDelay(10, TimeUnit.SECONDS)  // Schedule every 10 seconds
            .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }
}




package com.example.snycdata
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TimeUpdateWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        var updateCount = 0  // Track how many times the worker has run
    }

    override fun doWork(): Result {
        // Update the time in the UI
        updateTime()

        // Increment the update count
        updateCount++

        // Reschedule the worker if the update count is less than 4
        if (updateCount < 4) {
            scheduleNextUpdate()
        }

        return Result.success()
    }

    private fun updateTime() {
        // Get current time in a readable format
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

        // Update the UI by posting on the main thread
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            // Append the new time to the existing content of the TextView
            val currentText = MainActivity.textView?.text.toString()
            val updatedText = "$currentText\nCurrent Time: $currentTime"

            // Update the TextView with the previous and new times
            MainActivity.textView?.text = updatedText
        }
    }

    private fun scheduleNextUpdate() {
        val workRequest = OneTimeWorkRequestBuilder<TimeUpdateWorker>()
            .setInitialDelay(10, TimeUnit.SECONDS)  // Schedule every 10 seconds
            .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }
}

package com.example.snycdata
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {

    companion object {
        var textView: TextView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)

        // Start the initial WorkManager task to update time every 10 seconds
        startTimeUpdates()
    }

    private fun startTimeUpdates() {
        // Start the initial work request
        val workRequest = OneTimeWorkRequestBuilder<TimeUpdateWorker>().build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }
}

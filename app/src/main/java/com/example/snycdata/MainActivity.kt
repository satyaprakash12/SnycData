package com.example.snycdata

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private val objects = listOf("Object 1", "Object 2", "Object 3", "Object 4")
    private val displayedObjectsWithTime = mutableListOf<String>()
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)

        // Start the initial Worker (optional, just for rescheduling tasks in the background)
        val initialWorkRequest = OneTimeWorkRequestBuilder<Refresher>().build()
        WorkManager.getInstance(this).enqueue(initialWorkRequest)

        // Start updating the UI with coroutines
        startUIUpdates()
    }

    private fun startUIUpdates() {
        lifecycleScope.launch(Dispatchers.Main) {
            while (true) {
                updateUI()

                // Wait for 10 seconds before adding the next object
                delay(10000)  // 10000 ms = 10 seconds
            }
        }
    }

    private fun updateUI() {
        // Get current time in a readable format
        val currentTime = getCurrentTime()

        // Add the next object if available
        if (currentIndex < objects.size) {
            // Add the object with its corresponding time
            val objectWithTime = "${objects[currentIndex]} - Added at $currentTime"
            displayedObjectsWithTime.add(objectWithTime)
            currentIndex++
        }

        // Update the TextView with the list of objects and their timestamps
        textView.text = displayedObjectsWithTime.joinToString(separator = "\n")
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}


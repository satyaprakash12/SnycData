// MainActivity.kt
package com.example.snycdata.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.snycdata.data.TimeAdapter
import com.example.snycdata.data.TimeModel
import com.example.snycdata.data.TimeUpdateWorker
import com.example.snycdata.databinding.ActivityMainBinding
import com.example.snycdata.viewModels.TimeViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var timeAdapter: TimeAdapter
    private val timeViewModel: TimeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView with Adapter
        timeAdapter = TimeAdapter(mutableListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = timeAdapter

        // Observe the ViewModel's LiveData
        timeViewModel.timeList.observe(this, Observer { times ->
            timeAdapter.updateTimes(times)
        })

        // Register EventBus
        EventBus.getDefault().register(this)

        // Start the initial WorkRequest
        val workRequest = OneTimeWorkRequestBuilder<TimeUpdateWorker>()
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister EventBus
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTimeEvent(event: TimeModel) {
        // Add the new time to the ViewModel
        timeViewModel.addTime(event.time)
    }
}

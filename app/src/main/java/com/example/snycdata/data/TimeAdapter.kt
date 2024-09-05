package com.example.snycdata.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.snycdata.databinding.ItemTimeBinding

class TimeAdapter(private var timeList: MutableList<String>) : RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {

    class TimeViewHolder(val binding: ItemTimeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val binding = ItemTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.binding.timeTextView.text = timeList[position]
    }

    override fun getItemCount(): Int = timeList.size

    fun updateTimes(newTimes: List<String>) {
        timeList.clear()
        timeList.addAll(newTimes)
        notifyDataSetChanged()
    }
}

package com.binar.sciroper.ui.fragments.achievement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.sciroper.data.retrofit.HistoryResponse
import com.binar.sciroper.databinding.HistoryCardBinding
import com.binar.sciroper.util.getStringTimeStampWithDate

class HistoryAdapter(val history: List<HistoryResponse.History>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(private var binding: HistoryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(history: HistoryResponse.History) {
            binding.apply {
                gameMode.text = history.mode
                gameResult.text = history.result
                timestamp.text = history.createdAt.getStringTimeStampWithDate()
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HistoryViewHolder(HistoryCardBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = history[position]
        holder.bind(history)
    }

    override fun getItemCount(): Int {
        return history.size
    }
}
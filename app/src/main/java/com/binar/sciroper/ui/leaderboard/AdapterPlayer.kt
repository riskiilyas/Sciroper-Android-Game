package com.binar.sciroper.ui.leaderboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.databinding.ActivityItemLeaderboardBinding
import com.binar.sciroper.ui.leaderboard.AdapterPlayer.DataPlayerViewHolder

class AdapterPlayer (
    private val context: LeaderBoardActivity,

    private val dataPlayer: List<User>,
) : RecyclerView.Adapter<DataPlayerViewHolder>() {


    class DataPlayerViewHolder(private val itemBinding: ActivityItemLeaderboardBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindView(dataPlayer: User) {
            itemBinding.tvRank.text = dataPlayer.id.toString()
            itemBinding.namaPlayer.text = dataPlayer.username
            itemBinding.AvatarPlayer.setImageResource(dataPlayer.avatarId)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataPlayerViewHolder {
        val itemBinding =
            ActivityItemLeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataPlayerViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: DataPlayerViewHolder, position: Int) {
        val user: User = dataPlayer[position]
        holder.bindView(user)
    }

    override fun getItemCount(): Int = dataPlayer.size

}







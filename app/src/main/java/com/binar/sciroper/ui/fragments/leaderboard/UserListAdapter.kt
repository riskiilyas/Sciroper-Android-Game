package com.binar.sciroper.ui.fragments.leaderboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.databinding.ActivityItemLeaderboardBinding

class UserListAdapter(private val leaderBoardVm: LeaderBoardVm) :
    ListAdapter<User, UserListAdapter.UserViewHolder>(DiffCallback) {
    private var isInLeaderboard = false

    class UserViewHolder(private var binding: ActivityItemLeaderboardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            binding.tvRank.text = (layoutPosition + 1).toString()
            binding.namaPlayer.text = user.username
            binding.AvatarPlayer.setImageResource(user.avatarId)
            binding.tvLeaderboardLevel.text = user.level.toString()
            binding.tvLeaderboardPoint.text = "${user.point} Pts"
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(
            ActivityItemLeaderboardBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = getItem(position)
        holder.bind(currentUser)
        isInLeaderboard = true
        leaderBoardVm.setObserver(this::updateState)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        isInLeaderboard = false
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateState() {
        if (isInLeaderboard) notifyDataSetChanged()
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.idBinar == newItem.idBinar
            }
        }
    }
}
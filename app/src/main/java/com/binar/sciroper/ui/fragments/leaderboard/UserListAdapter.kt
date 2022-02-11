package com.binar.sciroper.ui.fragments.leaderboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.databinding.ActivityItemLeaderboardBinding

class UserListAdapter :
    ListAdapter<User, UserListAdapter.UserViewHolder>(DiffCallback) {

    class UserViewHolder(private var binding: ActivityItemLeaderboardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvRank.text = (layoutPosition+1).toString()
            binding.namaPlayer.text = user.username
            binding.AvatarPlayer.setImageResource(user.avatarId)
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
    }

//    fun getUserPosition(user: User) = currentList.indexOf(user)

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
package com.binar.sciroper.ui.leaderboard

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.databinding.ItemLeaderboardBinding
import com.binar.sciroper.ui.leaderboard.Adapter.DataPlayerViewHolder

class Adapter(
    private val context: Context,
    private val dataPlayer: MutableList<User>,
//    val listener: (User) -> Unit
) : RecyclerView.Adapter<DataPlayerViewHolder>() {


    class DataPlayerViewHolder(private val itemBinding: ItemLeaderboardBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindView(dataPlayer: User) {
            itemBinding.tvRank.text = dataPlayer.id.toString()
            itemBinding.namaPlayer.text = dataPlayer.username
//            itemBinding.AvatarPlayer.setImageResource(Int) = dataPlayer.avatar_id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataPlayerViewHolder {
        val itemBinding =
            ItemLeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataPlayerViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: DataPlayerViewHolder, position: Int) {
        val user: User = dataPlayer[position]
        holder.bindView(user)
    }

    override fun getItemCount(): Int = dataPlayer.size

}







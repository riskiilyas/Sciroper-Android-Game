package com.binar.sciroper.ui.leaderboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.binar.sciroper.data.db.AppDB
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.databinding.ActivityLeaderBoardBinding
import com.binar.sciroper.util.AvatarHelper
import com.binar.sciroper.util.UserLevel

class LeaderBoardActivity : AppCompatActivity(), MainView {
    private lateinit var binding: ActivityLeaderBoardBinding
    private lateinit var presenterMain: PresenterBoard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenterMain = PresenterBoard(this)

        presenterMain.getData().observe(this,{
            binding.tvRankDua.text = UserLevel.sortUsersLevel(it)[1].username
            binding.rankDua.setImageResource(UserLevel.sortUsersLevel(it)[1].avatarId)
            binding.tvRankSatu.text = UserLevel.sortUsersLevel(it)[0].username
            binding.rankSatu.setImageResource(UserLevel.sortUsersLevel(it)[0].avatarId)
            binding.tvRankTiga.text = UserLevel.sortUsersLevel(it)[2].username
            binding.rankTiga.setImageResource(UserLevel.sortUsersLevel(it)[2].avatarId)
        })
        presenterMain.getData().observe(this, {
            val recyclerView = binding.rcPlayer
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = AdapterPlayer(UserLevel.sortUsersLevel(it))
            binding.rcPlayer.apply {
                recyclerView.adapter
            }
        })
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}



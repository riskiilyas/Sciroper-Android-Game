package com.binar.sciroper.ui.leaderboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.binar.sciroper.data.db.AppDB
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.databinding.ActivityLeaderBoardBinding

class LeaderBoardActivity : AppCompatActivity(), MainView {
    private lateinit var binding: ActivityLeaderBoardBinding
    private lateinit var presenterMain: PresenterBoard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenterMain = PresenterBoard(this)
        presenterMain.getData().observe(this, {
            val recyclerView = binding.rcPlayer
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = AdapterPlayer(this, it)
            binding.rcPlayer.apply {
                recyclerView.adapter
            }
        })
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}



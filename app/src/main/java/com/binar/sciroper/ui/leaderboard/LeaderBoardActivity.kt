package com.binar.sciroper.ui.leaderboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.sciroper.databinding.ActivityLeaderBoardBinding

class LeaderBoardActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLeaderBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}
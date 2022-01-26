package com.binar.sciroper.ui.leaderboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.sciroper.databinding.ActivityItemLeaderboardBinding

class ItemLeaderboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemLeaderboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}
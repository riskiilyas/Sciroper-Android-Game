package com.binar.sciroper.ui.leaderboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.sciroper.R
import com.binar.sciroper.databinding.ItemLeaderboardBinding

class ItemBoardActivity : AppCompatActivity() {
    private lateinit var binding: ItemLeaderboardBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ItemLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

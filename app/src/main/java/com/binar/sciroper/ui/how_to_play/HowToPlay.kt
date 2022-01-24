package com.binar.sciroper.ui.how_to_play

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.sciroper.databinding.ActivityHowToPlayBinding


class HowToPlay : AppCompatActivity() {
    private lateinit var binding: ActivityHowToPlayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHowToPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackHTP.setOnClickListener()
        {
            finish()
        }
    }
}
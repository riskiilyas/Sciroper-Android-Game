package com.binar.sciroper.ui.playgame

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.sciroper.R
import com.binar.sciroper.databinding.ActivityPlayGameBinding
import com.binar.sciroper.ui.playervsplayer.PlayerActivity
import com.google.android.material.snackbar.Snackbar
import com.bumptech.glide.Glide

@SuppressLint("SetTextI18n")
class PlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")

        binding.tvPemainVsPemain.text = "$name VS Pemain"
        binding.tvPemainVsCPU.text = "$name VS CPU"

        binding.ivPemainVsPemain.setOnClickListener {
            val mIntent = Intent(this, PlayerActivity::class.java)
            mIntent.putExtra("name", name)
            startActivity(mIntent)

        }
        binding.ivPemainVsCPU.setOnClickListener {
            val mIntent = Intent(this, CPUActivity::class.java)
            mIntent.putExtra("name", name)
            startActivity(mIntent)
        }

        Snackbar.make(
            binding.menuActivity,
            "Selamat datang $name",
            Snackbar.LENGTH_LONG
        ).setAction("TUTUP") {
        }.show()
    }
}
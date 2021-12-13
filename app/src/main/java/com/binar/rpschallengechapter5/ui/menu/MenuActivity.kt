package com.binar.rpschallengechapter5.ui.menu

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.binar.rpschallengechapter5.R
import com.binar.rpschallengechapter5.databinding.ActivityMenuBinding
import com.binar.rpschallengechapter5.ui.player.PlayerActivity
import com.binar.rpschallengechapter5.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.bumptech.glide.Glide

@RequiresApi(Build.VERSION_CODES.M)
@SuppressLint("SetTextI18n")
class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")

        binding.tvPemainVsPemain.text = "$name VS Pemain"
        binding.tvPemainVsCPU.text = "$name VS CPU"


        Glide.with(this)
            .load(getString(R.string.url_landing_page1))
            .into(binding.ivPemainVsPemain)

        Glide.with(this)
            .load(getString(R.string.url_landing_page2))
            .into(binding.ivPemainVsCPU)

        binding.ivPemainVsPemain.setOnClickListener {
            val mIntent = Intent(this, PlayerActivity::class.java)
            mIntent.putExtra("name", name)
            startActivity(mIntent)

        }
        binding.ivPemainVsCPU.setOnClickListener {
            val mIntent = Intent(this, MainActivity::class.java)
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
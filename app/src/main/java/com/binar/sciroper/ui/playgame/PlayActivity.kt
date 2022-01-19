package com.binar.sciroper.ui.playgame


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.sciroper.R
import com.binar.sciroper.databinding.ActivityPlayGameBinding
import com.binar.sciroper.util.goToWithData
import com.google.android.material.snackbar.Snackbar

class PlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val avatar = intent.getIntExtra("avatar", 0)

        binding.apply {
            tvPemainVsPemain.text = getString(R.string.player_vs_player, name)
            tvPemainVsCPU.text = getString(R.string.player_vs_player, name)
        }

        binding.ivPemainVsPemain.setOnClickListener {
            goToWithData(PlayerActivity::class.java) {
                it.putExtra("name", name)
                it.putExtra("avatar", avatar)
            }
        }
        binding.ivPemainVsCPU.setOnClickListener {
            goToWithData(CPUActivity::class.java) {
                it.putExtra("name", name)
                it.putExtra("avatar", avatar)
            }
        }
        Snackbar.make(
            binding.playActivity,
            "Selamat datang $name",
            Snackbar.LENGTH_LONG
        ).setAction("TUTUP") {
        }.show()
    }
}
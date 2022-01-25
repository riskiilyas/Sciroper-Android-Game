package com.binar.sciroper.ui.menugameplay


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.sciroper.R
import com.binar.sciroper.databinding.ActivityMenuGamePlayBinding
import com.binar.sciroper.ui.playervsplayer.PlayerActivity
import com.binar.sciroper.ui.playgame.CPUActivity
import com.binar.sciroper.util.goto

class MenuGamePlayActivity : AppCompatActivity(), MGPView {

    private lateinit var binding: ActivityMenuGamePlayBinding
    private lateinit var presenterMGP: PresenterMGP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuGamePlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenterMGP = PresenterMGP(this)
        val username = presenterMGP.getDataUser().username

        binding.apply {
            tvPemainVsPemain.text = getString(R.string.player_vs_player, username)
            tvPemainVsCPU.text = getString(R.string.player_vs_com, username)
        }

        binding.ivPemainVsPemain.setOnClickListener {
            goto(PlayerActivity::class.java)
        }
        binding.ivPemainVsCPU.setOnClickListener {
            goto(CPUActivity::class.java)
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}
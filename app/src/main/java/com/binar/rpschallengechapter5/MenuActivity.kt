package com.binar.rpschallengechapter5

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.binar.rpschallengechapter5.databinding.ActivityMenuBinding
import com.google.android.material.snackbar.Snackbar
import com.bumptech.glide.Glide

@RequiresApi(Build.VERSION_CODES.M)
@SuppressLint("SetTextI18n")
class MenuActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PERSON = "extra_person"
    }

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user: User = intent.getParcelableExtra<User>(EXTRA_PERSON) as User

        binding.tvPemainVsPemain.text = "${user.name} VS Pemain"
        binding.tvPemainVsCPU.text = "${user.name} VS CPU"


        Glide.with(this)
            .load(getString(R.string.url_landing_page1))
            .into(binding.ivPemainVsPemain)

        Glide.with(this)
            .load(getString(R.string.url_landing_page2))
            .into(binding.ivPemainVsCPU)

        binding.ivPemainVsPemain.setOnClickListener {
            val mIntent = Intent(this, PlayerActivity::class.java)
            mIntent.putExtra(PlayerActivity.EXTRA_PERSON, user)
            startActivity(mIntent)

        }
        binding.ivPemainVsCPU.setOnClickListener {
            val mIntent = Intent(this, MainActivity::class.java)
            mIntent.putExtra(MainActivity.EXTRA_PERSON, user)
            startActivity(mIntent)
        }

        Snackbar.make(
            binding.menuActivity,
            "Selamat datang ${user.name}",
            Snackbar.LENGTH_LONG
        ).setAction("TUTUP") {
        }.show()
    }
}
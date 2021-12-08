package com.binar.rpschallengechapter5

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.binar.rpschallengechapter5.databinding.ActivityMainBinding

@RequiresApi(Build.VERSION_CODES.M)
@SuppressLint("ResourceAsColor")
open class MainActivity : AppCompatActivity(), Callback {
    companion object {
        const val EXTRA_PERSON = "extra_person"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user: User = intent.getParcelableExtra<User>(EXTRA_PERSON) as User

        binding.pemain1.text = user.name


        val btnPemain = arrayOf(
            binding.ivPemain1Batu,
            binding.ivPemain1Kertas,
            binding.ivPemain1Gunting,
        )

        val btnCom = arrayOf(
            binding.ivComBatu,
            binding.ivComKertas,
            binding.ivComGunting,
        )

        val controller = Controller(this)

        btnPemain.forEachIndexed { index, ImageView ->
            ImageView.setOnClickListener {

                val hasilCom = btnCom.random()
                val hasilPemain = btnPemain[index].contentDescription.toString()
                Log.d("PEMAIN SATU", "Memilih $hasilPemain")
                Toast.makeText(this, "Pemain 1 Memilih $hasilPemain", Toast.LENGTH_SHORT)
                    .show()
                hasilCom.setBackgroundResource(R.drawable.shape_background)
                disableClick(false)
                controller.cekSuit(
                    hasilPemain,
                    hasilCom.contentDescription.toString()
                )
                btnPemain.forEach {
                    it.setBackgroundResource(android.R.color.transparent)
                }
                btnPemain[index].setBackgroundResource(R.drawable.shape_background)
            }
        }
        binding.ivRefresh.setOnClickListener {
            Log.d("reset", "Clicked")
            btnPemain.forEach {
                it.setBackgroundResource(android.R.color.transparent)
            }
            btnCom.forEach {
                it.setBackgroundResource(android.R.color.transparent)
            }
            hasil(R.string.vs, android.R.color.transparent, R.color.red)
            disableClick(true)
        }

        binding.ivClose.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra(MenuActivity.EXTRA_PERSON, user)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)

//            supportFragmentManager.beginTransaction()
//                .add(R.id.landing_page, Page3Fragment())
//                .addToBackStack(null)
//                .commit()
        }

    }

    private fun disableClick(isEnable: Boolean) {
        binding.ivPemain1Kertas.isEnabled = isEnable
        binding.ivPemain1Batu.isEnabled = isEnable
        binding.ivPemain1Gunting.isEnabled = isEnable
    }

    override fun hasil(text: Int, bg: Int, textColor: Int) {
        binding.textHasil.text = getString(text)
        binding.textHasil.setBackgroundColor(getColor(bg))
        binding.textHasil.setTextColor(getColor(textColor))
    }

}
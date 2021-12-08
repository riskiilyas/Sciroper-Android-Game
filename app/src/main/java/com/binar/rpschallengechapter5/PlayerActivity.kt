package com.binar.rpschallengechapter5

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.binar.rpschallengechapter5.databinding.ActivityPlayerBinding

@RequiresApi(Build.VERSION_CODES.M)
@SuppressLint("ResourceAsColor")
open class PlayerActivity : AppCompatActivity(), Callback {

    companion object {
        const val EXTRA_PERSON = "extra_person"
    }

    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user: User = intent.getParcelableExtra<User>(EXTRA_PERSON) as User
        binding.pemain1.text = user.name


        val btnPemainSatu = arrayOf(
            binding.ivPemain1Batu,
            binding.ivPemain1Kertas,
            binding.ivPemain1Gunting,
        )

        val btnPemainDua = arrayOf(
            binding.ivPemain2Batu,
            binding.ivPemain2Kertas,
            binding.ivPemain2Gunting,
        )

        var hasilPemainSatu = ""
        var hasilPemainDua: String

        disableClick2(false)
        val controller = Controller(this)
        btnPemainSatu.forEachIndexed { index, ImageView ->
            Log.e("pemain satu", "klikk")
            ImageView.setOnClickListener {
                hasilPemainSatu = btnPemainSatu[index].contentDescription.toString()
                Log.d("PEMAIN SATU","Memilih $hasilPemainSatu")
                Toast.makeText(this, "Pemain 1 Memilih $hasilPemainSatu", Toast.LENGTH_SHORT)
                    .show()
                disableClick1(false)
                disableClick2(true)

                btnPemainSatu.forEach {
                    it.setBackgroundResource(android.R.color.transparent)
                }
                btnPemainSatu[index].setBackgroundResource(R.drawable.shape_background)
            }
        }

        btnPemainDua.forEachIndexed { index, ImageView ->
            Log.e("pemain Dua", "klikk")
            ImageView.setOnClickListener {
                hasilPemainDua = btnPemainDua[index].contentDescription.toString()
                Log.d("PEMAIN DUA","Memilih $hasilPemainDua")
                Toast.makeText(this, "Pemain 1 Memilih $hasilPemainDua", Toast.LENGTH_SHORT)
                    .show()
                disableClick2(false)
                if (hasilPemainSatu != "") {
                    controller.cekSuit(hasilPemainSatu, hasilPemainDua)
                }

                btnPemainDua.forEach {
                    it.setBackgroundResource(android.R.color.transparent)
                }
                btnPemainDua[index].setBackgroundResource(R.drawable.shape_background)
            }
        }

        binding.ivRefresh.setOnClickListener {
            Log.d("reset", "Clicked")
            btnPemainSatu.forEach {
                it.setBackgroundResource(android.R.color.transparent)
            }
            btnPemainDua.forEach {
                it.setBackgroundResource(android.R.color.transparent)
            }
            hasil(R.string.vs, android.R.color.transparent, R.color.red)
            disableClick1(true)
            disableClick2(false)

            hasilPemainSatu = ""
            hasilPemainDua = ""
        }

        binding.ivClose.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra(MenuActivity.EXTRA_PERSON, user)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

    }

    private fun disableClick1(isEnable: Boolean) {
        binding.ivPemain1Kertas.isEnabled = isEnable
        binding.ivPemain1Batu.isEnabled = isEnable
        binding.ivPemain1Gunting.isEnabled = isEnable
    }

    private fun disableClick2(isEnable: Boolean) {
        binding.ivPemain2Kertas.isEnabled = isEnable
        binding.ivPemain2Batu.isEnabled = isEnable
        binding.ivPemain2Gunting.isEnabled = isEnable
    }

    override fun hasil(text: Int, bg: Int, textColor: Int) {
        binding.textHasil.text = getString(text)
        binding.textHasil.setBackgroundColor(getColor(bg))
        binding.textHasil.setTextColor(getColor(textColor))
    }

}

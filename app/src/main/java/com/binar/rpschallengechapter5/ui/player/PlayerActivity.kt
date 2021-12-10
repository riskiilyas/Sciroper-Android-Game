package com.binar.rpschallengechapter5.ui.player

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.binar.rpschallengechapter5.R
import com.binar.rpschallengechapter5.controller.Callback
import com.binar.rpschallengechapter5.controller.Controller
import com.binar.rpschallengechapter5.databinding.ActivityPlayerBinding
import com.binar.rpschallengechapter5.ui.dialog.DialogResult


@RequiresApi(Build.VERSION_CODES.M)
@SuppressLint("ResourceAsColor")
open class PlayerActivity : AppCompatActivity(), Callback {

    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        binding.pemain1.text = name


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
                Log.d("PEMAIN SATU", "Memilih $hasilPemainSatu")
                Toast.makeText(this, "$name Memilih $hasilPemainSatu", Toast.LENGTH_SHORT)
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
                Log.d("PEMAIN DUA", "Memilih $hasilPemainDua")
                Toast.makeText(this, "Pemain 2 Memilih $hasilPemainDua", Toast.LENGTH_SHORT)
                    .show()
                disableClick2(false)
                if (hasilPemainSatu != "") {
                    controller.cekSuit(hasilPemainSatu, hasilPemainDua, name, "Pemain 2")
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
            binding.textHasil.text = getString(R.string.vs)
            binding.textHasil.setTextColor(getColor(R.color.red))
//            hasil(R.string.vs, android.R.color.transparent, R.color.red)
            disableClick1(true)
            disableClick2(false)

            hasilPemainSatu = ""
            hasilPemainDua = ""
        }

        binding.ivClose.setOnClickListener {
            finish()
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

    override fun hasil(hasil: String) {
        val dialogResult = DialogResult()
        val bundle = Bundle()
        bundle.putString("hasil", hasil)
        dialogResult.arguments = bundle
        dialogResult.show(supportFragmentManager, "DialogResult")
    }
}

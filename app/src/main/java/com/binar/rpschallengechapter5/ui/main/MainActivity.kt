package com.binar.rpschallengechapter5.ui.main

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.binar.rpschallengechapter5.R
import com.binar.rpschallengechapter5.controller.CallBackFragment
import com.binar.rpschallengechapter5.controller.Callback
import com.binar.rpschallengechapter5.controller.Controller
import com.binar.rpschallengechapter5.databinding.ActivityMainBinding
import com.binar.rpschallengechapter5.ui.dialog.DialogResult


@RequiresApi(Build.VERSION_CODES.M)
@SuppressLint("ResourceAsColor")
open class MainActivity : AppCompatActivity(), Callback, CallBackFragment {

    private lateinit var binding: ActivityMainBinding
    val name by lazy { intent.getStringExtra("name") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.pemain1.text = name


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


        val controller = Controller(this, name, "CPU")
        btnPemain.forEachIndexed { index, ImageView ->
            ImageView.setOnClickListener {
                val hasilCom = btnCom.random()
                val hasilPemain = btnPemain[index].contentDescription.toString()

                Log.d("PEMAIN SATU", "Memilih $hasilPemain")
                Toast.makeText(this, "$name Memilih $hasilPemain", Toast.LENGTH_SHORT)
                    .show()
                disableClick(false)
                hasilCom.setBackgroundResource(R.drawable.shape_background)
                controller.cekSuit(
                    hasilPemain,
                    hasilCom.contentDescription.toString())
                Log.d("CPU", "Memilih $hasilCom")
                Toast.makeText(
                    this,
                    "CPU Memilih ${hasilCom.contentDescription}",
                    Toast.LENGTH_LONG
                )
                    .show()
                btnPemain.forEach {
                    it.setBackgroundResource(android.R.color.transparent)
                }
                btnPemain[index].setBackgroundResource(R.drawable.shape_background)
            }
        }

        binding.ivRefresh.setOnClickListener {
            Log.d("reset", "Clicked")
            reset(android.R.color.transparent)
        }

        binding.ivClose.setOnClickListener {
            finish()

        }
    }


    private fun disableClick(isEnable: Boolean) {
        binding.ivPemain1Kertas.isEnabled = isEnable
        binding.ivPemain1Batu.isEnabled = isEnable
        binding.ivPemain1Gunting.isEnabled = isEnable
    }


    override fun hasil(hasil: String) {
        val dialogResult = DialogResult()
        val bundle = Bundle()
        bundle.putString("hasil", hasil)
        dialogResult.arguments = bundle
        dialogResult.show(supportFragmentManager, "DialogResult")
    }

    override fun reset(
        bgPilihan: Int
    ) {
        binding.ivPemain1Batu.setBackgroundResource(bgPilihan)
        binding.ivPemain1Kertas.setBackgroundResource(bgPilihan)
        binding.ivPemain1Gunting.setBackgroundResource(bgPilihan)
        binding.ivComBatu.setBackgroundResource(bgPilihan)
        binding.ivComKertas.setBackgroundResource(bgPilihan)
        binding.ivComGunting.setBackgroundResource(bgPilihan)
        disableClick(true)

    }

}

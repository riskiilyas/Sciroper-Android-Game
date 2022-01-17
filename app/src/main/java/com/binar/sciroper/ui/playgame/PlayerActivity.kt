package com.binar.sciroper.ui.playgame

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.binar.sciroper.R
import com.binar.sciroper.databinding.ActivityPlayerBinding


@RequiresApi(Build.VERSION_CODES.M)
@SuppressLint("ResourceAsColor")
open class PlayerActivity : AppCompatActivity(), Callback, CallBackFragment {

    private val binding by lazy { ActivityPlayerBinding.inflate(layoutInflater) }
    val name by lazy { intent.getStringExtra("name") }
    private var hasilPemainSatu = ""
    private var hasilPemainDua = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.pemain1.text = name
        showToast(this,"$name Duluan yahh")


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


        disableClick2(false)
        val controller = Controller(this, name, "Pemain 2")
        btnPemainSatu.forEachIndexed { index, ImageView ->
            Log.d("pemain satu", "klikk")
            ImageView.setOnClickListener {
                hasilPemainSatu = btnPemainSatu[index].contentDescription.toString()

                Log.d("PEMAIN SATU", "Memilih $hasilPemainSatu")
                showToast(this,"$name Memilih $hasilPemainSatu")

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
                showToast(this,"Pemain 2 Memilih $hasilPemainDua")
                disableClick2(false)
                if (hasilPemainSatu != "") {
                    controller.cekSuit(hasilPemainSatu, hasilPemainDua)
                    btnPemainDua.forEach {
                        it.setBackgroundResource(android.R.color.transparent)
                    }
                    btnPemainDua[index].setBackgroundResource(R.drawable.shape_background)
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

    override fun reset(
        bgPilihan: Int
    ) {
        binding.apply {
            ivPemain1Batu.setBackgroundResource(bgPilihan)
            ivPemain1Kertas.setBackgroundResource(bgPilihan)
            ivPemain1Gunting.setBackgroundResource(bgPilihan)
            ivPemain2Batu.setBackgroundResource(bgPilihan)
            ivPemain2Kertas.setBackgroundResource(bgPilihan)
            ivPemain2Gunting.setBackgroundResource(bgPilihan)
        }
        hasilPemainSatu = ""
        hasilPemainDua = ""
        disableClick1(true)
        disableClick2(false)

    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

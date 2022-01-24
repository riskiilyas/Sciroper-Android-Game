package com.binar.sciroper.ui.playgame

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.binar.sciroper.R
import com.binar.sciroper.databinding.ActivityCpuBinding


@SuppressLint("ResourceAsColor")
open class CPUActivity : AppCompatActivity(), PlayView, DialogView {

    private lateinit var binding: ActivityCpuBinding
    val name by lazy { intent.getStringExtra("name") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCpuBinding.inflate(layoutInflater)
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


        val controller = PresenterPlayImpl(this, name, "CPU")

        btnPemain.forEachIndexed { index, ImageView ->
            ImageView.setOnClickListener {
                val hasilCom = btnCom.random()
                val hasilPemain = btnPemain[index].contentDescription.toString()

                Log.d("PEMAIN SATU", "Memilih $hasilPemain")
                showToast(this, "$name Memilih $hasilPemain")
                disableClick(false)

                hasilCom.setBackgroundResource(R.drawable.shape_background)
                controller.cekSuit(
                    hasilPemain,
                    hasilCom.contentDescription.toString()
                )
                Log.d("CPU", "Memilih $hasilCom")
                showToast(this, "CPU Memilih ${hasilCom.contentDescription}")
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
        binding.apply {
            ivPemain1Kertas.isEnabled = isEnable
            ivPemain1Batu.isEnabled = isEnable
            ivPemain1Gunting.isEnabled = isEnable
        }
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
            ivComBatu.setBackgroundResource(bgPilihan)
            ivComKertas.setBackgroundResource(bgPilihan)
            ivComGunting.setBackgroundResource(bgPilihan)
        }
        disableClick(true)

    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}

package com.binar.sciroper.ui.register_confirmation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.databinding.ActivityRegisterConfirmationBinding
import com.binar.sciroper.ui.menu.MenuActivity

class RegisterConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}
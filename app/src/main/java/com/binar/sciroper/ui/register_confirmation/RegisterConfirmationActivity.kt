package com.binar.sciroper.ui.register_confirmation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.sciroper.databinding.ActivityRegisterConfirmationBinding
import com.binar.sciroper.ui.menu.MenuActivity

class RegisterConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent?.extras?.getInt("key_id")!!

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("key_id", userId)
            startActivity(intent)
        }
    }
}
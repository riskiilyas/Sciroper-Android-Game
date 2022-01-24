package com.binar.sciroper.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.binar.sciroper.R
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.data.local.AppSharedPreference.isDarkMode
import com.binar.sciroper.databinding.ActivitySettingBinding
import com.binar.sciroper.ui.profile.Profile
import com.binar.sciroper.util.goto
import com.google.android.material.switchmaterial.SwitchMaterial

class Setting : AppCompatActivity() {
    var userId: Int = 0
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = AppSharedPreference.id!!

        // btn back
        binding.ivBackSetting.setOnClickListener()
        {
            finish()
        }

        binding.tvProfile.setOnClickListener {
            goto(Profile::class.java)
        }
            //tes
        // dark mode
        val btn = findViewById<SwitchMaterial>(R.id.switchDark)
        btn.setOnCheckedChangeListener { buttonView, isChecked ->
            if (btn.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                btn.text = "Disable dark mode"
                isDarkMode = true

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                isDarkMode = false
            }
        }
    }
}
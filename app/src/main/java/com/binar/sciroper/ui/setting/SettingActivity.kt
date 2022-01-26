package com.binar.sciroper.ui.setting

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.binar.sciroper.R
import com.binar.sciroper.data.local.AppSharedPreference.isDarkMode
import com.binar.sciroper.databinding.ActivitySettingBinding
import com.binar.sciroper.ui.profile.ProfileActivity
import com.binar.sciroper.util.goto
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.switchDark.isChecked = isDarkMode!!

        binding.ivBackSetting.setOnClickListener()
        {
            finish()
        }

        binding.btnProfile.setOnClickListener {
            goto(ProfileActivity::class.java)
        }
        val btn = findViewById<SwitchMaterial>(R.id.switchDark)
        btn.setOnCheckedChangeListener { _, _ ->
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
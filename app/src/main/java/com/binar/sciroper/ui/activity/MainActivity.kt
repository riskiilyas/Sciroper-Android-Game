package com.binar.sciroper.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.binar.sciroper.R
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.util.BGMusic.playMusic

class MainActivity : AppCompatActivity() {
    var isMusicPlay: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (AppSharedPreference.isMusicPlay) {
            playMusic(this)
        }
    }
}
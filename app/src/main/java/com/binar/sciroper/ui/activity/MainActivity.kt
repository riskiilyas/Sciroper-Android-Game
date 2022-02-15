package com.binar.sciroper.ui.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.binar.sciroper.R
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.util.App
import com.binar.sciroper.util.BGMusic
import com.binar.sciroper.util.BGMusic.pausePlay
import com.binar.sciroper.util.BGMusic.playMusic
import com.binar.sciroper.util.Share

class MainActivity : AppCompatActivity() {
    var isMusicPlay: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStop() {
        super.onStop()
        pausePlay()
    }

    override fun onResume() {
        super.onResume()
        if (AppSharedPreference.isMusicPlay && App.isReady) playMusic()
    }
}
package com.binar.rpschallengechapter5.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.binar.rpschallengechapter5.R
import com.binar.rpschallengechapter5.ui.landing.LandingPageActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val delaySplashScreen = 2000L
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LandingPageActivity::class.java)
            startActivity(intent)
            finish()
        }, delaySplashScreen)
    }
}
package com.binar.sciroper.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.binar.sciroper.R

class MainActivity : AppCompatActivity() {
    var isMusicPlay: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}
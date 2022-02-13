package com.binar.sciroper.ui.activity

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.sciroper.R
import com.binar.sciroper.data.api.FirebaseService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseService.init()
    }
}
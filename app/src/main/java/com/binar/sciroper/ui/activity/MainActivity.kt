package com.binar.sciroper.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.binar.sciroper.R

class MainActivity : AppCompatActivity() {
    private lateinit var uri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val aa = Uri.parse("a").buildUpon().query("b.png").build()
        println(aa.query)
        Log.d("TESSS", "onCreate: $aa")

    }
}
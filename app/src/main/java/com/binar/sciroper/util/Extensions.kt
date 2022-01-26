package com.binar.sciroper.util

import android.content.Intent
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

fun EditText.grabText() = text.toString()

fun <T> AppCompatActivity.goto(theClass: Class<T>) {
    val intent = Intent(this, theClass)
    startActivity(intent)
}
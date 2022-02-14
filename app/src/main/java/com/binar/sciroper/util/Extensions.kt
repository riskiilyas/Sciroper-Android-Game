package com.binar.sciroper.util

import android.content.Intent
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun EditText.grabText() = text.toString()

fun <T> AppCompatActivity.goto(theClass: Class<T>) {
    val intent = Intent(this, theClass)
    startActivity(intent)
}

fun String.getStringTimeStampWithDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val dateTime = formatter.parse(this)

    val formatTime = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.getDefault())
    formatTime.timeZone= TimeZone.getTimeZone("GMT+14")
    return formatTime.format(dateTime)
}
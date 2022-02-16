package com.binar.sciroper.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.StrictMode
import android.util.Log
import android.view.View
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

object Share {

    fun getViewScreenshot(view: View, height: Int, width: Int): Bitmap? {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDrawable: Drawable = view.background
        Log.d("banana", "getViewScreenshot: $bgDrawable")
        bgDrawable.draw(canvas)
        view.draw(canvas)
        return bitmap
    }

    fun saveScreenshot(bitmap: Bitmap, context: Context) {


        val filename = "Screenshot.jpg"
        val defaultFile = File(context.getExternalFilesDir(null)!!.absolutePath + "/Screenshot")

        if (!defaultFile.exists()) defaultFile.mkdirs()
        var file = File(defaultFile, filename)
        if (file.exists()) {
            file.delete()
            file = File(defaultFile, filename)
        }
        val fos: FileOutputStream
        try {
            fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.e("Filenotfound", e.message, e)
        } catch (e: IOException) {
            Log.e("IOException", e.message, e)
        }

    }

    private var bmpUri: Uri? = null

    init {
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    fun ShareFileScreensshot(file: File?): Intent? {
        bmpUri = Uri.fromFile(file)
        return if (bmpUri != null) {
            val text = "Let's play Sciroper and invite your friends to play this game."
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, text)
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri)
            shareIntent.type = "image/*"
            shareIntent
        } else {
            null
        }
    }
}
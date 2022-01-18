package com.binar.sciroper.util

import android.app.Application
import android.content.Context
import com.binar.sciroper.data.db.AppDB
import java.lang.ref.WeakReference

class App: Application() {

    companion object {
        lateinit var context: WeakReference<Context>
        var appDb: AppDB? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = WeakReference(applicationContext)
    }
}
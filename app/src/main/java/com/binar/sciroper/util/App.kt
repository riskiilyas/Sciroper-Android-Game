package com.binar.sciroper.util

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.binar.sciroper.data.db.AppDB
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.firebase.FirebaseRtdb
import com.binar.sciroper.data.local.AppSharedPreference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.runBlocking
import java.lang.ref.WeakReference

class App : Application() {

    companion object {
        lateinit var context: WeakReference<Context>
        lateinit var appDb: AppDB
        var isReady = false
    }

    override fun onCreate() {
        super.onCreate()
        context = WeakReference(applicationContext)
        appDb = Room.databaseBuilder(
            applicationContext,
            AppDB::class.java,
            AppDB.DB_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
        AppSharedPreference.isLogin?.let {
            if (it) loadUser()
        }
        BGMusic.createMediaplayer(this)
        BGMusic.playMusic()
        BGMusic.pausePlay()
    }

    private fun loadUser() {
        val userDao = appDb.getUserDao()
        val database = FirebaseRtdb()
        val loadedUser = User()
        val target = database.database.getReference("Users").child(AppSharedPreference.idBinar!!)
        target.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                loadedUser.avatarId =
                    snapshot.child("avatarId").value.toString()
                        .toInt()
                loadedUser.level =
                    snapshot.child("level").value.toString()
                        .toInt()
                loadedUser.coin =
                    snapshot.child("coin").value.toString().toInt()
                loadedUser.idBinar =
                    snapshot.child("idBinar").value.toString()
                loadedUser.items =
                    snapshot.child("items").value.toString()
                loadedUser.password =
                    snapshot.child("password").value.toString()
                loadedUser.point =
                    snapshot.child("point").value.toString().toInt()
                loadedUser.username =
                    snapshot.child("username").value.toString()
                loadedUser.wins =
                    snapshot.child("wins").value.toString().toInt()
                loadedUser.loses =
                    snapshot.child("loses").value.toString().toInt()
                loadedUser.email =
                    snapshot.child("email").value.toString()

                runBlocking {
                    userDao.insertUser(loadedUser)
                    Log.i("onDataChange_getUserAvatarId", "$snapshot")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("onCancelled_getAvatarId", error.message)
            }
        })
    }
}
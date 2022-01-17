package com.binar.sciroper.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.db.user.UserDAO

@Database(entities = [User::class], exportSchema = false, version = 1)
abstract class AppDB: RoomDatabase() {

    abstract fun getUserDao(): UserDAO

    // Mendapatkan Instance appDB dengan konsep Singleton
    companion object {
        private const val DB_NAME = "app_db"

        @Volatile
        private var INSTANCE: AppDB? = null

        // Masukkan Context null jika sudah di Inisialisasi
        fun getInstance(applicationContext: Context?): AppDB {
            synchronized(Any()) {
                if (INSTANCE == null) {
                    INSTANCE = buildDB(applicationContext!!)
                }
                return INSTANCE!!
            }
        }

        private fun buildDB(context: Context): AppDB {
            return Room.databaseBuilder(context, AppDB::class.java, DB_NAME).build()
        }

    }

}
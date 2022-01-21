package com.binar.sciroper.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.db.user.UserDAO

@Database(entities = [User::class], exportSchema = false, version = 1)
abstract class AppDB: RoomDatabase() {
    abstract fun getUserDao(): UserDAO

    companion object {
        const val DB_NAME = "app_db"
    }
}
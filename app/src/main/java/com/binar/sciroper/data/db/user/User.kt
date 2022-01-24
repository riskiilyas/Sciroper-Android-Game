package com.binar.sciroper.data.db.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = User.TABLE_NAME)
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "avatar_id")
    val avatarId: Int = -1,

    @ColumnInfo(name = "wins")
    var wins: Int = 0,

    @ColumnInfo(name = "loses")
    var loses: Int = 0,

    @ColumnInfo(name = "level")
    var level: Int = 1,

    @ColumnInfo(name = "point")
    var point: Int = 0,
) {
    companion object {
        const val TABLE_NAME = "user_table"
    }
}


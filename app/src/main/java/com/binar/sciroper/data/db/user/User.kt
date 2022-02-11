package com.binar.sciroper.data.db.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = User.TABLE_NAME)
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @SerializedName(value = "username")
    @ColumnInfo(name = "username")
    var username: String = "",

    @SerializedName(value = "email")
    @ColumnInfo(name = "email")
    var email: String = "",

    @SerializedName(value = "password")
    @ColumnInfo(name = "password")
    var password: String = "",

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

    @ColumnInfo(name = "coin")
    var coin: Int = 0,

    @ColumnInfo(name = "achievement")
    var achievement: String = "",

    @ColumnInfo(name = "items")
    var items: String = ""
) {
    companion object {
        const val TABLE_NAME = "user_table"
    }
}


package com.binar.sciroper.data.db.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = User.TABLE_NAME)
data class User(
    @PrimaryKey
    @ColumnInfo(name = "id_binar")
    val idBinar: String = "",

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

    /* id:
        'a' = R.id.achievement11
        'b' = R.id.achievement12
        'c' = R.id.achievement13
        'd' = R.id.achievement14
    */
    @ColumnInfo(name = "achievement")
    var achievement: String = "",

    /* id:
        'a' = R.id.avatar31
        'b' = R.id.avatar32
        'c' = R.id.avatar33
        'd' = R.id.avatar34
        'e' = R.id.avatar41
        'f' = R.id.avatar42
        'g' = R.id.avatar43
        'h' = R.id.avatar44
     */
    @ColumnInfo(name = "items")
    var items: String = "",
) {
    companion object {
        const val TABLE_NAME = "user_table"
    }
}

data class AuthDetails(
    val email: String,
    val password: String,
    val username: String
)


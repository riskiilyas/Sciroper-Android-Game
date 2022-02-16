package com.binar.sciroper.data.firebase

import android.util.Log
import com.binar.sciroper.data.db.user.User
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseRtdb {
    val database =
        Firebase.database("https://sciroper-fd4fe-default-rtdb.asia-southeast1.firebasedatabase.app")
    val databaseRef = database.getReference("Users")


    // addUser to the firebase rtdb
    fun addUser(
        idBinar: String,
        email: String,
        username: String,
        password: String,
        avatarId: Int
    ) {
        val user = User(
            idBinar = idBinar,
            username = username,
            email = email,
            password = password,
            avatarId = avatarId
        )
        database.getReference("Users").child(idBinar).setValue(user).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.i("firebase add user success", "$user is added")
            } else {
                Log.i("firebase add user fail", "${it.exception}")
            }
        }
    }

    fun updateUser(user: User) {
        databaseRef.child(user.idBinar).setValue(user)
    }
}
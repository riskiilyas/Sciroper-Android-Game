package com.binar.sciroper.data.firebase

import android.util.Log
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.local.AppSharedPreference
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseRtdb {
    private val sharedPreference = AppSharedPreference
    val database =
        Firebase.database("https://sciroper-fd4fe-default-rtdb.asia-southeast1.firebasedatabase.app")
    val databaseRef = database.getReference("Users")
    val auth = Firebase.auth


    // addUser to the firebase rtdb
    fun addUser(idBinar: String,
                email: String,
                username: String,
                password: String,
                avatarId: Int) {
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

    // temporarily scrapped
    fun registerUser(email: String, username: String, password: String, avatarId: Int) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = auth.currentUser?.uid
                val user = User(
                    email = email,
                    username = username,
                    password = password,
                    avatarId = avatarId
                )
                Log.i("sign up", "registration successful")
                databaseRef.child(uid!!).setValue(user).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.i("sign up", "registration successful and data saved in firebase")
                    } else {
                        Log.i(
                            "sign up",
                            "registration successful, but data did not save in firebase"
                        )
                    }
                }
            } else {
                Log.i("sign up", "registration fail")
            }
        }
    }

    fun updateUser(user: User) {
        databaseRef.child(user.idBinar).setValue(user)
    }
}
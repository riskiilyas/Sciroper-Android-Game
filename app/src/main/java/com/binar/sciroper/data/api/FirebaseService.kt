package com.binar.sciroper.data.api

import com.binar.sciroper.data.db.user.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.Exception


object FirebaseService {
    private lateinit var database: FirebaseDatabase

    fun init() {
        database = Firebase.database("https://sciroper-fd4fe-default-rtdb.asia-southeast1.firebasedatabase.app/")
    }

    fun updateUser(
        user: User,
        key: String,
        onFailure: (e: Exception?)->Unit = {},
        onSuccess: ()->Unit = {},
        ) {
        database.getReference("Users").child(key).setValue(user).apply {
            addOnSuccessListener { onSuccess() }
            addOnFailureListener { onFailure(this.exception) }
        }
    }

    fun getUsers(): List<User> {

    }

    fun getUser(key: String): User {

    }
}
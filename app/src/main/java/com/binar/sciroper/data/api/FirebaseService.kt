package com.binar.sciroper.data.api

import com.binar.sciroper.BuildConfig
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.local.AppSharedPreference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object FirebaseService {
    private val database = Firebase.database("https://sciroper-fd4fe-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val ref = database.getReference("Users")

    fun regisOrUpdate(
        user: User, onResult : (Boolean) -> Unit) {
        ref.child(AppSharedPreference.username!!).setValue(user)
            .addOnFailureListener { onResult(false) }
            .addOnCompleteListener { onResult(true) }
    }

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
}
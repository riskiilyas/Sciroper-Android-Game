package com.binar.sciroper.data.retrofit

data class Data(
    val _id: String,
    val email: String,
    val username: String,
    val token: String?,
)

data class AuthResponse(
    val `data`: Data,
    val success: Boolean
)
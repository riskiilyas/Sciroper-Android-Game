package com.binar.sciroper.data.remote

import com.binar.sciroper.data.api.UserService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://binar-gdd-cc8.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userService = retrofit.create(UserService::class.java)

}
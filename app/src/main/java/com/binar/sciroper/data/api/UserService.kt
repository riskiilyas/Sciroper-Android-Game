package com.binar.sciroper.data.api

import com.binar.sciroper.data.remote.LoginData
import com.binar.sciroper.data.remote.ResponseApi
import com.squareup.okhttp.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {

    @POST("/api/v1/auth/register")
    suspend fun registUser(@Body loginData: LoginData): retrofit2.Call<ResponseApi>

    @GET("/api/v1/auth/login")
    suspend fun login(loginData: LoginData): ResponseApi

}
package com.binar.sciroper.data.retrofit

import com.binar.sciroper.data.db.user.AuthDetails
import okhttp3.RequestBody
import retrofit2.http.*

interface BinarApiService {
    @POST("api/v1/auth/register")
    suspend fun register(@Body body: AuthDetails): AuthResponse

    @POST("api/v1/auth/login")
    suspend fun login(@Body loginDetails: AuthDetails): AuthResponse

    @GET("api/v1/users")
    suspend fun getUser(
        @Header("Authorization") auth: String,
    ): AuthResponse

    @PUT("api/v1/users")
    suspend fun updateProfile(
        @Header("Authorization") authorization: String,
        @Body body: RequestBody
    ): AuthResponse
}
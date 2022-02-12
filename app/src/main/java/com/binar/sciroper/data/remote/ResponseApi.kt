package com.binar.sciroper.data.remote


import com.google.gson.annotations.SerializedName

data class ResponseApi(
    @SerializedName("errors")
    val errors: String,
    @SerializedName("success")
    val success: Boolean
)
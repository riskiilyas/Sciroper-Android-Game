package com.binar.sciroper.util

import com.binar.sciroper.data.retrofit.AuthResponse

sealed class UiState {
    object Loading : UiState()

    data class Success(
        val authResponse: AuthResponse
    ) : UiState()

    data class Error(val message: String) : UiState()
}
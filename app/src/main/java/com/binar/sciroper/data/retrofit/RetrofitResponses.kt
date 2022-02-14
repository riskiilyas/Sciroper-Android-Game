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

data class HistoryResponse(
    val data: List<History>,
    val success: Boolean
) {
    data class History(
        val _id: String?,
        val result: String,
        val mode: String,
        val message: String,
        val createdAt: String,
        val updatedAt: String
    )
}

data class GameResult(
    val mode: String = "Singleplayer",
    val result: String
)
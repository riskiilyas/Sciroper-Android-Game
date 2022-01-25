package com.binar.sciroper.ui.playgame


interface PlayView {
    fun hasil(hasil: String, status: Int)
    suspend fun comPlay(id: Int)
}
package com.binar.sciroper.ui.playervsplayer


interface PlayerView {
    fun result(result: String, resultLottie: Int)
    fun disableClick1(isEnable: Boolean)
    fun disableClick2(isEnable: Boolean)
}
package com.binar.sciroper.ui.playgame

interface PresenterPlay {
    fun comTurn(pilihanSatu: String)
    fun checkSuit(pilihanSatu: String, pilihanCom: Int)
}
package com.binar.sciroper.ui.playgame

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PresenterPlayImpl(
    private val playView: PlayView,
    private val namaPlayer: String,
    ) : PresenterPlay {

    override fun comTurn(
        pilihanSatu: String,
    ) {
        val comChoice = listOf(
            0, 1, 2
        ).random()

        GlobalScope.launch(Dispatchers.Default) {
            playView.comPlay(comChoice)
            GlobalScope.launch(Dispatchers.Main) {
                checkSuit(pilihanSatu, comChoice)
            }
        }
    }

    override fun checkSuit(pilihanSatu: String, pilihanCom: Int) {
        when (pilihanSatu) {
            "Batu" -> {
                when (pilihanCom) {
                    0 -> playView.hasil("SERI!", 0)
                    1 -> playView.hasil("COM\nMENANG!", -1)
                    2 -> playView.hasil("$namaPlayer\nMenang", 1)
                }
            }
            "Kertas" -> {
                when (pilihanCom) {
                    0 -> playView.hasil("$namaPlayer\nMenang", 1)
                    1 -> playView.hasil("SERI!", 0)
                    2 -> playView.hasil("COM\nMENANG!", -1)
                }
            }
            "Gunting" -> {
                when (pilihanCom) {
                    0 -> playView.hasil("COM\nMENANG!", -1)
                    1 -> playView.hasil("$namaPlayer\nMenang", 1)
                    2 -> playView.hasil("SERI!", 0)
                }
            }
        }
    }


}
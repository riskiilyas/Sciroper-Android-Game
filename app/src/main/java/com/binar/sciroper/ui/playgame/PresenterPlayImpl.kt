package com.binar.sciroper.ui.playgame

import android.util.Log

class PresenterPlayImpl(
    private val playView: PlayView,
    private val pemainSatu: String?,
    private val pemainDua: String
    ) : PresenterPlay {

    override fun cekSuit(
        pilihanSatu: String,
        pilihanDua: String,
    ) {
        if (pilihanSatu == pilihanDua) {
            Log.d("Hasil", "Draw")
            playView.hasil("SERI!")

        } else if (pilihanSatu == "Batu" && pilihanDua == "Gunting" || pilihanSatu == "Gunting" && pilihanDua == "Kertas" || pilihanSatu == "Kertas" && pilihanDua == "Batu") {
            Log.d("Hasil", "pemain 1 win")
            playView.hasil("$pemainSatu \n  MENANG!")

        } else {
            Log.d("Hasil", "pemain 2/pemainDua win")
            playView.hasil("$pemainDua \n MENANG!")
        }
        Log.d("Hasil", "$pilihanSatu VS $pilihanDua")
    }

}
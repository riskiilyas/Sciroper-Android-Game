package com.binar.rpschallengechapter5.controller

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi



class Controller(private val callback: Callback, private val pemainSatu: String?, private val pemainDua: String) : InterfaceController {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun cekSuit(
        pilihanSatu: String,
        pilihanDua: String,
     ) {
        if (pilihanSatu == pilihanDua) {
            Log.d("Hasil", "Draw")
            callback.hasil("SERI!")

        } else if (pilihanSatu == "Batu" && pilihanDua == "Gunting" || pilihanSatu == "Gunting" && pilihanDua == "Kertas" || pilihanSatu == "Kertas" && pilihanDua == "Batu") {
            Log.d("Hasil", "pemain 1 win")
            callback.hasil("$pemainSatu \n  MENANG!")

        } else {
            Log.d("Hasil", "pemain 2/pemainDua win")
            callback.hasil("$pemainDua \n MENANG!")
        }
        Log.d("Hasil", "$pilihanSatu VS $pilihanDua")
    }
}
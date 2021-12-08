package com.binar.rpschallengechapter5

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi


class Controller(private val callback: Callback) : InterfaceController {


    @RequiresApi(Build.VERSION_CODES.M)
    override fun cekSuit(pemain: String, pemainDua: String) {
        if (pemain == pemainDua) {
            Log.d("Hasil", "Draw")
            callback.hasil(R.string.draw, R.color.blue_draw, R.color.white)

        } else if (pemain == "Batu" && pemainDua == "Gunting" || pemain == "Gunting" && pemainDua == "Kertas" || pemain == "Kertas" && pemainDua == "Batu") {
            Log.d("Hasil", "pemain 1 win")
            callback.hasil(R.string.pemain_1_win, R.color.green_winlose, R.color.white)

        } else {
            Log.d("Hasil", "pemain 2/pemainDua win")
            callback.hasil(R.string.pemain_2_win, R.color.green_winlose, R.color.white)
        }
        Log.d("Hasil", "$pemain VS $pemainDua")
    }

}
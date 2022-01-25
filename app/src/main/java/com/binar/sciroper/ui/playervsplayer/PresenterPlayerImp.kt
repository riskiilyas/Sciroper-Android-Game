package com.binar.sciroper.ui.playervsplayer

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.binar.sciroper.R
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.util.App
import com.binar.sciroper.util.UserLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PresenterPlayerImp(
    private val playerView: PlayerView,
    private val firstPlayer: String?,
    private val secondPlayer: String
) : PresenterPlayer {
    private val idUser = AppSharedPreference.id!!


    var dataUser = App.appDb.getUserDao().getUserByIdNoLiveData(idUser)
    private val point = UserLevel(dataUser)


    override fun checkSuit(
        firstPlayerChoice: String,
        secondPlayerChoice: String,
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val user = App.appDb.getUserDao()

            if (firstPlayerChoice == secondPlayerChoice) {
                Log.d("Hasil", "Draw")
                launch(Dispatchers.Main) {
                    playerView.result("DRAW!", R.raw.result_draw)
                }

            } else if (firstPlayerChoice == "Rock" && secondPlayerChoice == "Scissors" || firstPlayerChoice == "Scissors" && secondPlayerChoice == "Paper" || firstPlayerChoice == "Paper" && secondPlayerChoice == "Rock") {
                Log.d("Hasil", "pemain 1 win")
                launch(Dispatchers.Main) {
                    playerView.result("$firstPlayer\nWIN!", R.raw.result_win)
                }
                point.win()
                user.updateUser(dataUser)

            } else {
                Log.d("Hasil", "pemain 2/pemainDua win")
                launch(Dispatchers.Main) {
                    playerView.result("$secondPlayer\nWIN!", R.raw.result_win)
                }
                point.lose()
                user.updateUser(dataUser)
            }

            Log.d("Hasil", "$firstPlayerChoice VS $secondPlayerChoice")
        }
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    companion object {
        const val DEFAULT_RESULT = ""
    }


}
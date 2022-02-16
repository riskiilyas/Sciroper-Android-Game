package com.binar.sciroper.ui.fragments.vs_player

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.firebase.FirebaseRtdb
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.data.retrofit.GameResult
import com.binar.sciroper.data.retrofit.Retrofit
import com.binar.sciroper.util.UserLevel
import com.binar.sciroper.util.checkNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VsPlayerVm(private val userDao: UserDAO) : ViewModel() {
    val user = userDao.getUserId(AppSharedPreference.idBinar!!)
    val userLiveData = userDao.getUserById(AppSharedPreference.idBinar!!)
    val firebase = FirebaseRtdb()
    private val _choices: List<String> = listOf("Rock", "Paper", "Scissors")
    private val choices get() = _choices


    private var _playerChoice: String = ""
    val playerChoice get() = _playerChoice
    private var _playerSelectedId: Int = 0
    fun setPlayerChoice(choice: String) {
        _playerChoice = choice
    }

    fun setPlayerSelectedId(id: Int) {
        _playerSelectedId = id
    }

    private var _opponentChoice: String = ""
    val opponentChoice get() = _opponentChoice
    private var _opponentSelectedId: Int = 0
    fun setOpponentChoice(choice: String) {
        _opponentChoice = choice
    }

    fun setOpponentSelectedId(id: Int) {
        _opponentSelectedId = id
    }

    private var _status: Boolean = true
    val status get() = _status
    fun setStatus(status: Boolean) {
        _status = status
    }


    private var _result: String = ""
    val result get() = _result
    private var _winner: String = ""
    val winner get() = _winner
    private var _opponent: String = "Player 2"
    val opponent get() = _opponent

    fun postResult(gameResult: String) {
        viewModelScope.launch {
            val outcome =
                when (gameResult) {
                    "win" -> "Player Win"
                    "lose" -> "Opponent Win"
                    "draw" -> "Draw"
                    else -> ""
                }
            try {
                Retrofit.retrofitService.postGameResult(
                    "Bearer ${AppSharedPreference.userToken!!}",
                    GameResult(result = outcome)
                )
                Log.i("result", "post $result")
            } catch (e: Exception) {
                Log.i("result", "${e.message}")
            }
        }
    }

    fun gameResult() {
        val userLevel = UserLevel(user)
        _result = if (playerChoice == opponentChoice) {
            userLevel.draw()
            _winner = "draw"
            "draw"
        } else if (playerChoice == choices[0] && opponentChoice == choices[2] ||
            playerChoice == choices[1] && opponentChoice == choices[0] ||
            playerChoice == choices[2] && opponentChoice == choices[1]
        ) {
            _winner = user.toString()
            userLevel.win()
            _winner = user.username
            "win"

        } else if (playerChoice == choices[0] && opponentChoice == choices[1] ||
            playerChoice == choices[1] && opponentChoice == choices[2] ||
            playerChoice == choices[2] && opponentChoice == choices[0]
        ) {
            userLevel.lose()
            _winner = opponent
            "lose"
        } else {
            ""
        }
        updateUser()
    }

    private fun updateUser() {
        checkNetworkAvailable {
            if (it) firebase.updateUser(user)
        }
        viewModelScope.launch(Dispatchers.Default) { userDao.updateUser(user) }
    }

    fun reset() {
        setOpponentChoice("")
        setPlayerChoice("")
        setOpponentSelectedId(0)
        setPlayerSelectedId(0)
        setStatus(true)
    }

    class VsPlayerVmFactory(private val userDao: UserDAO) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VsPlayerVm::class.java)) {
                return VsPlayerVm(userDao) as T
            }
            throw IllegalArgumentException("Unknown view model class")
        }
    }
}
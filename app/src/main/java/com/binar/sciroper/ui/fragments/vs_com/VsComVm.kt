package com.binar.sciroper.ui.fragments.vs_com

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.local.AppSharedPreference
import java.lang.IllegalArgumentException

class VsComVm(private val userDao: UserDAO) : ViewModel() {
    val user = userDao.getUserId(AppSharedPreference.id!!)
    val userLiveData = userDao.getUserById(AppSharedPreference.id!!)

    private val _choices: List<String> = listOf("Rock", "Paper", "Scissors")
    val choices get() = _choices

    private var _status: Boolean = true
    val status get() = _status
    fun setStatus(status: Boolean) {
        _status = status
    }

    private var _playerChoice = ""
    val playerChoice get() = _playerChoice
    private var _playerSelectedId = 0
    val playerSelectedId get() = _playerSelectedId
    fun setPlayerChoice(choice: String) {
        _playerChoice = choice
    }
    fun setPlayerSelectedId(id: Int) {
        _playerSelectedId = id
    }

    private var _computerChoice = ""
    val computerChoice get() = _computerChoice
    private var _computerSelectedId = 0
    val opponentSelectedId get() = _computerSelectedId
    fun setComputerChoice(){
        _computerChoice = _choices.random()
    }
    fun setOpponentSelectedId(id: Int) {
        _computerSelectedId = id
    }

    private var _result = MutableLiveData("")
    val result: LiveData<String> get() = _result
    private var _winner: String = ""
    val winner get() = _winner
    fun gameResult() {
        _result.value = if (playerChoice == computerChoice) {
            _winner = "draw"
            "draw"
        } else if (playerChoice == choices[0] && computerChoice == choices[2] ||
            playerChoice == choices[1] && computerChoice == choices[0] ||
            playerChoice == choices[2] && computerChoice == choices[1]
        ) {
            _winner = user.username
            "win"
        } else if (playerChoice == choices[0] && computerChoice == choices[1] ||
            playerChoice == choices[1] && computerChoice == choices[2] ||
            playerChoice == choices[2] && computerChoice == choices[0]
        ) {
            _winner = "Com"
            "lose"
        } else {
            ""
        }
        Log.d(
            "game_result",
            "Player: ${playerChoice}, Opponent: $computerChoice - Result: ${result.value}"
        )
        Log.d("game_result", "$opponentSelectedId")
    }
}

class VsComVmFactory(private val userDao: UserDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VsComVm::class.java)) {
            return VsComVm(userDao) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}
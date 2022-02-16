package com.binar.sciroper.ui.fragments.vs_com

import android.util.Log
import androidx.lifecycle.*
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.firebase.FirebaseRtdb
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.data.retrofit.GameResult
import com.binar.sciroper.data.retrofit.Retrofit
import com.binar.sciroper.util.UserLevel
import com.binar.sciroper.util.checkNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class VsComVm(private val userDao: UserDAO) : ViewModel() {
    val user = userDao.getUserId(AppSharedPreference.idBinar!!)
    val isReset = MutableLiveData(false)
    val isOver = MutableLiveData(false)
    val firebase = FirebaseRtdb()
    val userLiveData = userDao.getUserById(AppSharedPreference.idBinar!!)

    private val _choices: List<String> = listOf("Rock", "Paper", "Scissors")
    private val choices get() = _choices

    private var _playerChoice = ""
    private val playerChoice get() = _playerChoice
    fun setPlayerChoice(choice: String) {
        _playerChoice = choice
    }

    private var _computerChoice = ""
    private val computerChoice get() = _computerChoice

    fun setComChoice(choice: String) {
        _computerChoice = choice
    }

    private var _result = MutableLiveData("")
    val result: LiveData<String> get() = _result
    private var _winner: String = ""
    val winner get() = _winner

    fun postResult(gameResult: String) {
        viewModelScope.launch {
            val outcome =
                when (gameResult) {
                    "win" -> "Player Win"
                    "lose" -> "Opponent Win"
                    else -> "Draw"
                }
            try {
                Retrofit.retrofitService.postGameResult(
                    "Bearer ${AppSharedPreference.userToken!!}",
                    GameResult(result = outcome)
                )
                Log.i("result", "post ${result.value}")
            } catch (e: Exception) {
                Log.i("result", "${e.message}")
            }
        }
    }

    fun gameResult() {
        val userLevel = UserLevel(user)
        _result.value = if (playerChoice == computerChoice) {
            userLevel.draw()
            _winner = "draw"
            "draw"
        } else if (playerChoice == choices[0] && computerChoice == choices[2] ||
            playerChoice == choices[1] && computerChoice == choices[0] ||
            playerChoice == choices[2] && computerChoice == choices[1]
        ) {
            userLevel.win()
            _winner = user.username
            "win"
        } else if (playerChoice == choices[0] && computerChoice == choices[1] ||
            playerChoice == choices[1] && computerChoice == choices[2] ||
            playerChoice == choices[2] && computerChoice == choices[0]
        ) {
            userLevel.lose()
            _winner = "Com"
            "lose"
        } else {
            ""
        }
        updateUser()
        isReset.value = false
        isOver.value = true
    }

    private fun updateUser() {
        checkNetworkAvailable {
            if (it) firebase.updateUser(user)
        }
        viewModelScope.launch(Dispatchers.Default) { userDao.updateUser(user) }
    }

    fun reset() {
        isOver.value = false
        isReset.value = true
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
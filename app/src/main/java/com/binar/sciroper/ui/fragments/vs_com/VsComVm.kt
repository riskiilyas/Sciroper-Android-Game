package com.binar.sciroper.ui.fragments.vs_com

import android.util.Log
import androidx.lifecycle.*
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.util.UserLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class VsComVm(private val userDao: UserDAO) : ViewModel() {
    val user = userDao.getUserId(AppSharedPreference.id!!)
    val isReset = MutableLiveData(false)
    val isOver = MutableLiveData(false)
    val userLiveData = userDao.getUserById(AppSharedPreference.id!!)

    private val _choices: List<String> = listOf("Rock", "Paper", "Scissors")
    val choices get() = _choices

    private var _playerChoice = ""
    val playerChoice get() = _playerChoice
    fun setPlayerChoice(choice: String) {
        _playerChoice = choice
    }

    private var _computerChoice = ""
    val computerChoice get() = _computerChoice

    fun setComChoice(choice: String) {
        _computerChoice = choice
    }

    private var _result = MutableLiveData("")
    val result: LiveData<String> get() = _result
    private var _winner: String = ""
    val winner get() = _winner

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
        viewModelScope.launch(Dispatchers.Default) { userDao.updateUser(user) }
        isReset.value = false
        isOver.value = true
    }

    fun reset() {
        Log.d("comd", "reset: ")
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
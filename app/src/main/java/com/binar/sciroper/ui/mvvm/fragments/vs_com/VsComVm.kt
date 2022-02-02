package com.binar.sciroper.ui.mvvm.fragments.vs_com

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.local.AppSharedPreference
import java.lang.IllegalArgumentException

class VsComVm(private val userDao: UserDAO) : ViewModel() {
    val user = userDao.getUserId(AppSharedPreference.id!!)
    val userLiveData = userDao.getUserById(AppSharedPreference.id!!)

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
    private val computerChoice get() = _computerChoice
    fun setComputerChoice(choice: String){}
}

class VsComVmFactory(private val userDao: UserDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VsComVm::class.java)) {
            return VsComVm(userDao) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}
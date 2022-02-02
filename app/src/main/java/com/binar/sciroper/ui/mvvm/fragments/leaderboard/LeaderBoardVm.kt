package com.binar.sciroper.ui.mvvm.fragments.leaderboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.data.db.user.UserDAO

class LeaderBoardVm(private val userDao: UserDAO) : ViewModel() {
    val allUsers = userDao.getUsers()
    private val _userListSize = MutableLiveData<Int>()
    val userListSize: LiveData<Int> = _userListSize

    fun getUserListSize(userList:List<User>){
        _userListSize.value = userList.size
    }
}

class LeaderBoardVmFactory(private val userDao: UserDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LeaderBoardVm::class.java)) {
            return LeaderBoardVm(userDao) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}
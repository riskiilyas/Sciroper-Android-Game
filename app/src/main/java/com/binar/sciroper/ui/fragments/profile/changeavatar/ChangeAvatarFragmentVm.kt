package com.binar.sciroper.ui.fragments.profile.changeavatar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.util.App
import kotlinx.coroutines.launch

class ChangeAvatarFragmentVm(private val userDao: UserDAO) : ViewModel() {
    val userData = App.appDb.getUserDao().getUserById(AppSharedPreference.idBinar!!)

    fun changeAvatar(id: Int) {
        viewModelScope.launch {
            userDao.updateUser(userData.value!!.apply {
                this.avatarId = id
            })
        }

    }
}

class CAVMFactory(private val userDao: UserDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangeAvatarFragmentVm::class.java)) {
            return ChangeAvatarFragmentVm(userDao) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}
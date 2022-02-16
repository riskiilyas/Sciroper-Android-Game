package com.binar.sciroper.ui.fragments.menu_game_play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.local.AppSharedPreference

class MenuGamePlayVm(userDao: UserDAO) : ViewModel() {
    val user = userDao.getUserById(AppSharedPreference.idBinar!!)
}

class MenuGamePlayVmFactory(private val userDao: UserDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuGamePlayVm::class.java)) {
            return MenuGamePlayVm(userDao) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}
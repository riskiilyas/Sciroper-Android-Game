package com.binar.sciroper.ui.mvvm.fragments.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.sciroper.data.db.user.UserDAO
import com.binar.sciroper.data.local.AppSharedPreference

class MenuVm(private val userDao: UserDAO) : ViewModel() {
    private val sharedPreferences = AppSharedPreference

    private val userId = sharedPreferences.id
    private val _user = userDao.getUserById(userId!!)
    val user get() = _user

}

class MenuVmFactory(private val userDao: UserDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuVm::class.java)) {
            return MenuVm(userDao) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}
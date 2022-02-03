package com.binar.sciroper.ui.mvvm.fragments.achievement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.sciroper.data.db.user.UserDAO

class AchievementVm(private val userDao: UserDAO) : ViewModel() {

}

class AchievementVmFactory(private val userDao: UserDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AchievementVm::class.java)) {
            return AchievementVm(userDao) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}
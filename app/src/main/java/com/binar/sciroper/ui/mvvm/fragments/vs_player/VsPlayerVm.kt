package com.binar.sciroper.ui.mvvm.fragments.vs_player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.sciroper.data.db.user.UserDAO

class VsPlayerVm(private val userDao: UserDAO) : ViewModel() {
}

class VsPlayerVmFactory(private val userDao: UserDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VsPlayerVm::class.java)) {
            return VsPlayerVm(userDao) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}
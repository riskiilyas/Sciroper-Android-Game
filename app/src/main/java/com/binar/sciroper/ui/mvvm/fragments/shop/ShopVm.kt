package com.binar.sciroper.ui.mvvm.fragments.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.sciroper.data.db.user.UserDAO

class ShopVm(private val userDao: UserDAO) : ViewModel() {

}

class ShopVmFactory(private val userDao: UserDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopVm::class.java)) {
            return ShopVm(userDao) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}